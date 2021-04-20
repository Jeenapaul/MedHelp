package com.medhelp.ui.paymentInfo

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.medhelp.R
import com.medhelp.ui.localDb.roomDb.DatabaseClient
import com.medhelp.ui.localDb.roomDb.bean.UserCardDetails
import com.medhelp.ui.paymentInfo.utils.FourDigitCardFormatWatcher
import kotlinx.android.synthetic.main.fragment_payment_info.*


class PaymentInfoFragment : Fragment() {
    var creditCardInfo:List<UserCardDetails?>?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_payment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        creditCardInfo=DatabaseClient.getInstance(context).appDatabase.userCardDetailsDap()?.getAll()
        if (creditCardInfo?.isNotEmpty() == true){
            etCardNumber.setText(creditCardInfo!![0]?.cardNumber)
            etExpiry.setText(creditCardInfo!![0]?.expiryDate)
            etCVV.setText(creditCardInfo!![0]?.cvv)
        }
        etCardNumber.addTextChangedListener(FourDigitCardFormatWatcher())
        btSave.setOnClickListener { validateCard() }
    }

    fun validateCard() {

        clearAnyPreviousErrorMessages()
        val creditCardNumber: String = etCardNumber.getText().toString()
        val expirationDate: String = etExpiry.getText().toString()
        val cvv: String = etCVV.getText().toString()
        /*if (!CreditCard.isValidCardNumber(creditCardNumber)) {
            etCardNumber.setError(getString(R.string.invalid_card_number))
            etCardNumber.requestFocus()
        }*/
        if (TextUtils.isEmpty(creditCardNumber)&&creditCardNumber.length!=19) {
            etCardNumber.setError(getString(R.string.invalid_card_number))
            etCardNumber.requestFocus()
        } /*else if (!CreditCard.isValidExpirationDate(expirationDate)) {
            etExpiry.setError(getString(R.string.invalid_expiration_date))
            etExpiry.requestFocus()
        }*//* else if (!CreditCard.isValidCvv(creditCardNumber, cvv)) {
            etCVV.setError(getString(R.string.invalid_cvv))
            etCVV.requestFocus()
        }*/else {
            if (creditCardInfo!=null){
                if (creditCardInfo?.isEmpty()!!){
                    val cardDetails= UserCardDetails(1,creditCardNumber,expirationDate,cvv)
                    DatabaseClient.getInstance(context).appDatabase.userCardDetailsDap()?.insert(cardDetails)
                }else{
                    val cardDetails= creditCardInfo!![0]?.uid?.let { UserCardDetails(it,creditCardNumber,expirationDate,cvv) }
                    DatabaseClient.getInstance(context).appDatabase.userCardDetailsDap()?.update(cardDetails)

                }
                Toast.makeText(context, "Credit Card Detail Updated", Toast.LENGTH_SHORT).show()
            }
          //  closeSoftKeyboard()
         /*   val creditCard = CreditCard(creditCardNumber, expirationDate, cvv, firstName, lastName)
            alertDialog(submitCreditCard(creditCard), null, getString(R.string.ok))*/
        }
    }


    private fun clearAnyPreviousErrorMessages() {
        etCardNumber.setError(null)
        etExpiry.setError(null)
        etCVV.setError(null)

    }
}