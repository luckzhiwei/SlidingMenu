package slidingmenu.dreamfly.org.myapplication;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import slidingmenu.dreamfly.org.myapplication.custom.DefineScaleSlidingMenu;
import slidingmenu.dreamfly.org.myapplication.custom.DefineSlidingMenu;


public class MainActivity extends Activity {


    private DefineScaleSlidingMenu mDefineSlingMenu;
    private Button btn;
    private boolean isOpen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.define_sliding_layout);
        this.isOpen=false;
        this.mDefineSlingMenu=(DefineScaleSlidingMenu)this.findViewById(R.id.defslidmenu_detslignmenu_rootlayout);
        this.mDefineSlingMenu.setmMenuLayout(LayoutInflater.from(this).inflate(R.layout.menu_layout,null));
        View contentView=LayoutInflater.from(this).inflate(R.layout.content_layout, null);
        this.mDefineSlingMenu.setmContentLayout(contentView);
        this.mDefineSlingMenu.setMenuRaido(0.7f);
        this.mDefineSlingMenu.setIsOpenInit(true);
        this.mDefineSlingMenu.setIsScale(true);
        this.mDefineSlingMenu.setIsalpha(true);
        this.mDefineSlingMenu.setIsOpenInit(false);
//        this.mDefineSlingMenu.setContentScaleRaido(0.16f);

        btn=(Button)contentView.findViewById(R.id.btn_mainactivity_showmenu);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  if(isOpen){
                       mDefineSlingMenu.showMenu();
                  }else{
                      mDefineSlingMenu.closeMenu();
                  }
                  isOpen=!isOpen;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
