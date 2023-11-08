package com.devtwist.cardpaymentui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.devtwist.cardpaymentui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private var binding: ActivityMainBinding? = null
    private var cardNumPattern:String = "XXXX  XXXX  XXXX  XXXX"
    private var cardNum : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        binding!!.cardNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val numLen = binding!!.cardNum.length()
                cardNum = binding!!.cardNum.text.toString()

                if(numLen<4){
                    binding!!.cNoScreen.text =
                        cardNum+cardNumPattern.substring(cardNum.length )

                }else if(numLen>=4 && numLen<8){
                    binding!!.cNoScreen.text =
                        cardNum.substring(0, 4) + "  " + cardNum.substring(4, cardNum.length) +cardNumPattern.substring(cardNum.length + 2)

                }else if(numLen>=8 && numLen<12){
                    binding!!.cNoScreen.text =
                        cardNum.substring(0, 4) + "  " + cardNum.substring(4, 8) + "  " + cardNum.substring(8, cardNum.length) + cardNumPattern.substring(cardNum.length + 4)

                }else if(numLen>=12 && numLen<=16){
                    binding!!.cNoScreen.text =
                        cardNum.substring(0, 4) + "  " + cardNum.substring(4, 8) + "  " + cardNum.substring(8, 12) + "  " + cardNum.substring(12, cardNum.length) + cardNumPattern.substring(cardNum.length + 6)

                }

            }

            override fun afterTextChanged(p0: Editable?) {
                if(cardNum.length==16){
                    binding!!.cardName.requestFocus()
                }
            }
        })

        binding!!.cardName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding!!.cardName.text.toString().trim().length <1){
                    binding!!.nameScreen.text = "Card Holder Name"
                }
                else{
                    binding!!.nameScreen.text = binding!!.cardName.text.toString().trim()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding!!.expMon.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding!!.expMon.text.toString().length<1){
                    binding!!.monthScreen.text = "MM"
                }
                else{
                    binding!!.monthScreen.text = binding!!.expMon.text.toString()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding!!.expMon.text.toString().length==2){
                    binding!!.expYear.requestFocus()
                }
            }
        })

        binding!!.expYear.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding!!.expYear.text.toString().length<1){
                    binding!!.yearScreen.text = "YY"
                }
                else{
                    binding!!.yearScreen.text = binding!!.expYear.text.toString()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding!!.expYear.text.toString().length == 2){
                    binding!!.cardCvc.requestFocus()
                }
            }
        })

        binding!!.cardCvc.setOnFocusChangeListener { view, isFocused ->
            if (isFocused) {
                object : CountDownTimer(250,250){
                    override fun onTick(p0: Long) {
                        binding!!.cardFrontLayout.animate().rotationY(90f).scaleX(0.65f).scaleY(0.65f).setDuration(250).start()
                        binding!!.cardBackLayout.rotationY = -90f
                        binding!!.cardBackLayout.scaleX= 0.65f
                        binding!!.cardBackLayout.scaleY = 0.65f

                    }

                    override fun onFinish() {
                        binding!!.cardFrontLayout.visibility = View.GONE
                        binding!!.cardBackLayout.visibility = View.VISIBLE
                        binding!!.cardBackLayout.animate().rotationY(0f).scaleX(1f).scaleY(1f).setDuration(250).start()

                    }

                }.start()
            }
            else{
                object : CountDownTimer(250,250){
                    override fun onTick(p0: Long) {
                        binding!!.cardBackLayout.animate().rotationY(-90f).scaleX(0.65f).scaleY(0.65f).setDuration(250).start()

                    }

                    override fun onFinish() {
                        binding!!.cardBackLayout.visibility = View.GONE
                        binding!!.cardFrontLayout.visibility = View.VISIBLE
                        binding!!.cardFrontLayout.animate().rotationY(0f).scaleX(1f).scaleY(1f).setDuration(250).start()

                    }

                }.start()
            }

        }

        binding!!.cardCvc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding!!.cvcScreen.text = binding!!.cardCvc.text.toString() + "XXX"
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding!!.cardCvc.length()==3){
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                    binding!!.cardCvc.clearFocus()
                }
            }
        })


    }
}