package com.dba.majika.ui.pembayaran

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.dba.majika.MainActivity
import com.dba.majika.R
import com.dba.majika.api.RetrofitClient
import com.dba.majika.databinding.FragmentPembayaranBinding
import com.dba.majika.models.PaymentResponse
import com.dba.majika.ui.menu.MenuFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PembayaranFragment : Fragment() {
    companion object {
        fun newInstance() = PembayaranFragment()
    }

    private var _binding: FragmentPembayaranBinding? = null
    private lateinit var codeScanner: CodeScanner

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPembayaranBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        codeScanner = CodeScanner(requireContext(), binding.pembayaranQrCode)
        codeScanner.decodeCallback = DecodeCallback {
            RetrofitClient.api.postPayment(it.text).enqueue(
                object : Callback<PaymentResponse> {
                    override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                        Log.d("retrofit", "qr error")
                        binding.pembayaranImage.setImageResource(R.drawable.question_mark_24px)
                        binding.pembayaranStatus.text = getText(R.string.payment_error)
                        binding.pembayaranStatusDescription.text =
                            getText(R.string.payment_error_network)

                        // Restart
                        codeScanner.stopPreview()
                        codeScanner.startPreview()
                    }

                    override fun onResponse(
                        call: Call<PaymentResponse>,
                        response: Response<PaymentResponse>
                    ) {
                        Log.d("retrofit", "qr successful")
                        val result = response.body()
                        if (result?.status == "SUCCESS") {
                            Log.d("payment", "success")
                            binding.pembayaranImage.setImageResource(R.drawable.ic_check_circle_24)
                            binding.pembayaranStatus.text = getText(R.string.payment_success)
                            binding.pembayaranStatusDescription.text =
                                getText(R.string.payment_payed)
                            codeScanner.releaseResources()

                            // Redirect to main activity (menu)
                            Thread.sleep(5_000)
                            val intent = Intent(context, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            startActivity(intent)

                        } else if (result?.status == "FAILED") {
                            Log.d("payment", "failed")
                            binding.pembayaranImage.setImageResource(R.drawable.ic_decline)
                            binding.pembayaranStatus.text = getText(R.string.payment_failed)
                            binding.pembayaranStatusDescription.text =
                                getText(R.string.payment_not_payed)

                            // Restart
                            codeScanner.stopPreview()
                            codeScanner.startPreview()

                        } else {
                            Log.d("payment", "bad request")
                            binding.pembayaranImage.setImageResource(R.drawable.question_mark_24px)
                            binding.pembayaranStatus.text = getText(R.string.payment_error)
                            binding.pembayaranStatusDescription.text =
                                getText(R.string.payment_error_qr)

                            // Restart
                            codeScanner.stopPreview()
                            codeScanner.startPreview()
                        }
                    }
                }
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // viewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}
