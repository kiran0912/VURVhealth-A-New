package com.VURVhealth.vurvhealth.vision;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.VURVhealth.vurvhealth.R;

public class VisionFilterActivity extends AppCompatActivity {
    private static boolean femaleClick = false;
    private static boolean maleClick = false;
    private TextView female;
    private LinearLayout llFemale;
    private LinearLayout ll_male;
    private TextView male;
    private ImageView sort_check1;
    private ImageView sort_check2;
    private TextView tvApply;
    private TextView tvReset;

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionFilterActivity$1 */
    class C08031 implements OnClickListener {
        C08031() {
        }

        public void onClick(View v) {
            if (VisionFilterActivity.this.sort_check1.getVisibility() == View.VISIBLE) {
                VisionFilterActivity.this.sort_check1.setVisibility(View.INVISIBLE);
                VisionFilterActivity.maleClick = false;
                return;
            }
            VisionFilterActivity.maleClick = true;
            VisionFilterActivity.this.sort_check1.setVisibility(View.VISIBLE);
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionFilterActivity$2 */
    class C08042 implements OnClickListener {
        C08042() {
        }

        public void onClick(View v) {
            if (VisionFilterActivity.this.sort_check2.getVisibility() == View.VISIBLE) {
                VisionFilterActivity.femaleClick = false;
                VisionFilterActivity.this.sort_check2.setVisibility(View.INVISIBLE);
                return;
            }
            VisionFilterActivity.femaleClick = true;
            VisionFilterActivity.this.sort_check2.setVisibility(View.VISIBLE);
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionFilterActivity$3 */
    class C08053 implements OnClickListener {
        C08053() {
        }

        public void onClick(View v) {
            VisionFilterActivity.femaleClick = false;
            VisionFilterActivity.maleClick = false;
            VisionFilterActivity.this.sort_check1.setVisibility(View.INVISIBLE);
            VisionFilterActivity.this.sort_check2.setVisibility(View.INVISIBLE);
        }
    }

    /* renamed from: com.VURVhealth.VURVhealth.vision.VisionFilterActivity$4 */
    class C08064 implements OnClickListener {
        C08064() {
        }

        public void onClick(View v) {
            Intent i = new Intent(VisionFilterActivity.this, VisionListActivity.class);
            i.putExtra("male", VisionFilterActivity.this.sort_check1.getVisibility() == View.VISIBLE ? "male" : "");
            i.putExtra("female", VisionFilterActivity.this.sort_check2.getVisibility() == View.VISIBLE ? "female" : "");
            VisionFilterActivity.this.setResult(101, i);
            VisionFilterActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_prescription_screen);
        this.tvApply = (TextView) findViewById(R.id.tvApply);
        this.tvReset = (TextView) findViewById(R.id.tvReset);
        this.male = (TextView) findViewById(R.id.tvPrice);
        this.female = (TextView) findViewById(R.id.tvDistance);
        this.ll_male = (LinearLayout) findViewById(R.id.llPrice);
        this.llFemale = (LinearLayout) findViewById(R.id.llDistance);
        this.sort_check1 = (ImageView) findViewById(R.id.sort_check2);
        this.sort_check2 = (ImageView) findViewById(R.id.sort_check1);
        this.male.setText(getResources().getString(R.string.male));
        this.female.setText(getResources().getString(R.string.female));
        this.ll_male.setOnClickListener(new C08031());
        this.llFemale.setOnClickListener(new C08042());
        this.tvReset.setOnClickListener(new C08053());
        this.tvApply.setOnClickListener(new C08064());
    }

    protected void onResume() {
        super.onResume();
        if (maleClick) {
            this.sort_check1.setVisibility(View.VISIBLE);
        } else {
            this.sort_check1.setVisibility(View.GONE);
        }
        if (femaleClick) {
            this.sort_check2.setVisibility(View.VISIBLE);
        } else {
            this.sort_check2.setVisibility(View.GONE);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
