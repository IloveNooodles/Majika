package com.dba.majika.ui.twibbon

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.hardware.camera2.*
import android.hardware.camera2.params.StreamConfigurationMap
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.util.SparseIntArray
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dba.majika.databinding.FragmentTwibbonBinding
import java.lang.Float.max
import java.nio.ByteBuffer
import java.util.*


class TwibbonFragment : Fragment() {

    private var _binding: FragmentTwibbonBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var textureView: TextureView
    private lateinit var cameraId: String
    private lateinit var backgroundHandlerThread: HandlerThread
    private lateinit var backgroundHandler: Handler
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraDevice: CameraDevice
    private lateinit var captureRequestBuilder: CaptureRequest.Builder
    private lateinit var cameraCaptureSession: CameraCaptureSession
    private lateinit var imageReader: ImageReader
    private lateinit var previewSize: Size
    private var cameraPermission = false
    private var cameraAvailable = false

    private var orientations : SparseIntArray = SparseIntArray(4).apply {
        append(Surface.ROTATION_0, 0)
        append(Surface.ROTATION_90, 90)
        append(Surface.ROTATION_180, 180)
        append(Surface.ROTATION_270, 270)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!wasCameraPermissionWasGiven()) {
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    cameraPermission = true
                } else {
                    Toast.makeText(requireActivity(), "Camera cannot be accessed", Toast.LENGTH_SHORT).show()
                }
            }.launch(Manifest.permission.CAMERA)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(TwibbonViewModel::class.java)

        _binding = FragmentTwibbonBinding.inflate(inflater, container, false)
        val root: View = binding.root

        textureView = binding.cameraPreview
        return root
    }
    override fun onResume(){
        super.onResume()
        startBackgroundThread()
        Log.d("onStart", cameraPermission.toString())
        if (cameraPermission){
            cameraManager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
            binding.takePhotoBtn.apply{
                setOnClickListener {
                    takePhoto()
                }
            }

            textureView.surfaceTextureListener = surfaceTextureListener
            if (textureView.isAvailable)
                connectCamera()

        }
        if (cameraAvailable) setupCamera();
    }

    override fun onPause() {
        super.onPause()
        stopBackgroundThread()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    /* Camera stuff */
    private fun wasCameraPermissionWasGiven() : Boolean {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        {
            cameraPermission = true
            return true
        }
        return cameraPermission
    }
    private fun setupCamera() {
        if(cameraManager == null){
            return
        }
        val cameraIds: Array<String> = cameraManager.cameraIdList
        Log.d("camera", "ids:${cameraIds.size}")
        cameraId = cameraIds[0]
        for (id in cameraIds) {
            val cameraCharacteristics = cameraManager.getCameraCharacteristics(id)

            //If we want to choose the rear facing camera instead of the front facing one
            if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) {
                Log.d("camera", "front")
                val streamConfigurationMap : StreamConfigurationMap? = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

                if (streamConfigurationMap != null) {
                    previewSize = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!.getOutputSizes(
                        ImageFormat.JPEG).maxByOrNull { it.height * it.width }!!
                    imageReader = ImageReader.newInstance(previewSize.width, previewSize.height, ImageFormat.JPEG, 1)
                    imageReader.setOnImageAvailableListener(onImageAvailableListener, backgroundHandler)
                }
                cameraId = id
                cameraAvailable = true
                Log.d("camera", "id:${cameraId.toString()}")
                return
            }
            Toast.makeText(requireActivity(), "Front camera cannot be accessed", Toast.LENGTH_SHORT).show()
            cameraAvailable = false

        }
    }
    private fun takePhoto() {
        if (!cameraAvailable) return
        captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
        captureRequestBuilder.addTarget(imageReader.surface)
        val rotation = requireActivity().windowManager.defaultDisplay.rotation
        captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, orientations.get(rotation))
        cameraCaptureSession.capture(captureRequestBuilder.build(), captureCallback, null)
    }

    @SuppressLint("MissingPermission")
    private fun connectCamera() {
        cameraManager.openCamera(cameraId, cameraStateCallback, backgroundHandler)
    }


    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        @SuppressLint("MissingPermission")
        override fun onSurfaceTextureAvailable(texture: SurfaceTexture, width: Int, height: Int) {
            if (cameraPermission) {
                setupCamera()
                connectCamera()
            }
        }

        override fun onSurfaceTextureSizeChanged(texture: SurfaceTexture, width: Int, height: Int) {

        }

        override fun onSurfaceTextureDestroyed(texture: SurfaceTexture): Boolean {
            return true
        }

        override fun onSurfaceTextureUpdated(texture: SurfaceTexture) {

        }
    }
    /**
     * Camera State Callbacks
     */

    private val cameraStateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            val surfaceTexture : SurfaceTexture? = textureView.surfaceTexture
            surfaceTexture?.setDefaultBufferSize(previewSize.width, previewSize.height)
            val previewSurface: Surface = Surface(surfaceTexture)

            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder.addTarget(previewSurface)

            cameraDevice.createCaptureSession(listOf(previewSurface, imageReader.surface), captureStateCallback, null)
        }

        override fun onDisconnected(cameraDevice: CameraDevice) {

        }

        override fun onError(cameraDevice: CameraDevice, error: Int) {
            val errorMsg = when(error) {
                ERROR_CAMERA_DEVICE -> "Fatal (device)"
                ERROR_CAMERA_DISABLED -> "Device policy"
                ERROR_CAMERA_IN_USE -> "Camera in use"
                ERROR_CAMERA_SERVICE -> "Fatal (service)"
                ERROR_MAX_CAMERAS_IN_USE -> "Maximum cameras in use"
                else -> "Unknown"
            }
            Log.e("camera", "Error when trying to connect camera $errorMsg")
        }
    }

    /**
     * Capture State Callback
     */

    private val captureStateCallback = object : CameraCaptureSession.StateCallback() {
        override fun onConfigureFailed(session: CameraCaptureSession) {

        }
        override fun onConfigured(session: CameraCaptureSession) {
            cameraCaptureSession = session

            cameraCaptureSession.setRepeatingRequest(
                captureRequestBuilder.build(),
                null,
                backgroundHandler
            )
        }
    }
    /**
     * Capture Callback
     */
    private val captureCallback = object : CameraCaptureSession.CaptureCallback() {

        override fun onCaptureStarted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            timestamp: Long,
            frameNumber: Long
        ) {}

        override fun onCaptureProgressed(
            session: CameraCaptureSession,
            request: CaptureRequest,
            partialResult: CaptureResult
        ) { }

        override fun onCaptureCompleted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            result: TotalCaptureResult
        ) {

        }
    }


    /**
     * ImageAvailable Listener
     */
    private val onImageAvailableListener = ImageReader.OnImageAvailableListener { reader ->
        val image: Image = reader.acquireLatestImage()
        val buffer: ByteBuffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        val bitmapImage: Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)

        val d = requireActivity().resources.getIdentifier(
            "@drawable/twibbon",
            "drawable",
            requireParentFragment().requireContext().packageName
        )
        val twibbon: Bitmap = BitmapFactory.decodeResource(requireContext().resources, d)
        val startX = max(bitmapImage.width / 2f - twibbon.width / 2f, 0f)
        val startY = max(bitmapImage.height/2f - twibbon.height/2f, 0f)
        val scaledBitmap: Bitmap = Bitmap.createBitmap(bitmapImage, startX.toInt(), startY.toInt(), twibbon.width, twibbon.height)

        val matrix = Matrix()
        matrix.postRotate(requireActivity().windowManager.defaultDisplay.rotation.toFloat())
        val rotatedBmp: Bitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        val bmOverlay = Bitmap.createBitmap(bitmapImage.width, bitmapImage.height, bitmapImage.config)
        val canvas = Canvas(bmOverlay)
        canvas.drawBitmap(rotatedBmp, Matrix(), null)
        canvas.drawBitmap(twibbon, 0f, 0f, null)

        val toast = Toast(context)
        val view = ImageView(context)
        view.setImageBitmap(bmOverlay)
        toast.setView(view)
        toast.show()
        Log.d("image", image.toString())
        image.close()
    }

    /**
     * Background Thread
     */
    private fun startBackgroundThread() {
        backgroundHandlerThread = HandlerThread("CameraVideoThread")
        backgroundHandlerThread.start()
        backgroundHandler = Handler(backgroundHandlerThread.looper)
    }

    private fun stopBackgroundThread() {
        backgroundHandlerThread.quitSafely()
        backgroundHandlerThread.join()
    }
}
