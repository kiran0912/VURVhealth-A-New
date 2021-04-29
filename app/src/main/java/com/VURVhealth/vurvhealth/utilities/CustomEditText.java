package com.VURVhealth.vurvhealth.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.VURVhealth.vurvhealth.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by YQLabs on 22-09-2015.
 */
@SuppressLint("AppCompatCustomView")
public class CustomEditText extends EditText {

    Context context;
    Toast toast;
    int cursorPosition=0;
    boolean cursorBool=false;

    public CustomEditText(final Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        //CustomEditText.this.setFilters(new InputFilter[]{new InputFilter.AllCaps()});


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            setImeOptions(EditorInfo.IME_FLAG_FORCE_ASCII);

//        InputFilter filter = new InputFilter() {
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end,
//                                       Spanned dest, int dstart, int dend) {
//
//                if (source instanceof SpannableStringBuilder) {
//                    SpannableStringBuilder sourceAsSpannableBuilder = (SpannableStringBuilder)source;
//                    for (int i = end - 1; i >= start; i--) {
//                        char currentChar = source.charAt(i);
//                        if (!Character.isLetterOrDigit(currentChar) && !Character.isSpaceChar(currentChar)) {
//                            sourceAsSpannableBuilder.delete(i, i+1);
//                        }
//                    }
//                    return source;
//                } else {
//                    StringBuilder filteredStringBuilder = new StringBuilder();
//                    for (int i = start; i < end; i++) {
//                        char currentChar = source.charAt(i);
//                        if (Character.isLetterOrDigit(currentChar) || Character.isSpaceChar(currentChar)) {
//                            filteredStringBuilder.append(currentChar);
//                        }
//                    }
//                    return filteredStringBuilder.toString();
//                }
//            }
//        };



        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            this.setBackgroundResource(android.R.drawable.edit_text);


        }

//        setInputType(InputType.TYPE_CLASS_TEXT);

//        setFilters(new InputFilter[]{filter });

//        setKeyListener(DigitsKeyListener.getInstance("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
//                        android:digits="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890@.,:;'!$%#^*()[]{}"



        String value = "";
        final String viewMode = "editing";
        final String viewSide = "right";
        final Drawable x = getResources().getDrawable(R.drawable.cross_clear);

        // The height will be set the same with [X] icon
        setHeight(x.getBounds().height());

        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        Drawable x2 = viewMode.equals("never") ? null : viewMode
                .equals("always") ? x : viewMode.equals("editing") ? (value
                .equals("") ? null : x)
                : viewMode.equals("unlessEditing") ? (value.equals("") ? x
                : null) : null;
        // Display search icon in text field
        final Drawable searchIcon = getResources().getDrawable(
                android.R.drawable.ic_search_category_default);
        searchIcon.setBounds(0, 0, x.getIntrinsicWidth(),
                x.getIntrinsicHeight());

        setCompoundDrawables(null, null, viewSide.equals("right") ? x2
                : null, null);



        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getCompoundDrawables()[viewSide.equals("left") ? 0 : 2] == null) {
                    return false;
                }
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                // x pressed
                if ((viewSide.equals("left") && event.getX() < getPaddingLeft()
                        + x.getIntrinsicWidth())
                        || (viewSide.equals("right") && event.getX() > getWidth()
                        - getPaddingRight() - x.getIntrinsicWidth())) {
                    Drawable x3 = viewMode.equals("never") ? null : viewMode
                            .equals("always") ? x
                            : viewMode.equals("editing") ? null : viewMode
                            .equals("unlessEditing") ? x : null;
                    setText("");
                    setCompoundDrawables(null, null,
                            viewSide.equals("right") ? x3 : null, null);
                }
                return false;
            }
        });
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


//                Log.v("tag","naga>>>>>>>"+start+"  "+before+"  "+count);
                Drawable x4 = viewMode.equals("never") ? null : viewMode
                        .equals("always") ? x
                        : viewMode.equals("editing") ? (getText().toString()
                        .equals("") ? null : x) : viewMode
                        .equals("unlessEditing") ? (getText()
                        .toString().equals("") ? x : null) : null;
                setCompoundDrawables(null, null,
                        viewSide.equals("right") ? x4 : null, null);

//                Log.e("tag", "cursorBool>>>>>>>>>>>>>>>>>" + cursorBool + "   " + cursorPosition);

                if(cursorBool){
                    CustomEditText.this.setSelection(cursorPosition);
                    cursorBool=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                Pattern regex = Pattern.compile("[^A-Za-z0-9$&+_\"~`,:;=?@#|'<>.^*()%!-^\n^\\s]");

                if(s.toString().length() > 0){

                    if(getSelectionStart()!=0)
                        cursorPosition=getSelectionStart() - 1;

                    String nonenglishChar="";
                    try{
                         nonenglishChar=s.toString().substring(cursorPosition, cursorPosition + 1);

                    } catch (StringIndexOutOfBoundsException e){

                    } catch (Exception e){
                         nonenglishChar=s.toString().substring(cursorPosition-1, cursorPosition);
                    }

                    Matcher match =  regex.matcher(nonenglishChar);

                    if(!match.matches()){
                    }else {

                        String test="";


                        try{
                             test=s.toString().split(nonenglishChar)[0]+s.toString().split(nonenglishChar)[1];
                        }catch(Exception e){
                             test=s.toString().split(nonenglishChar)[0];
                        }


                        cursorBool=true;
                        CustomEditText.this.setText(test);

                        CustomEditText.this.setFilters(new InputFilter[]{new InputFilter.AllCaps()});


                        if(toast==null){
                            toast = Toast.makeText(context,
                                    "Please enter only english characters as input", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }


                    }
                }




            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                if(toast!=null){
                    toast.cancel();

                    toast=null;
                }



            }
        });
    }



}
