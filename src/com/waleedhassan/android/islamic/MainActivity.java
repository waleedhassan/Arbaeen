package com.waleedhassan.android.islamic;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;


public class MainActivity extends SherlockFragmentActivity {
	
	MyAdapter mAdapter;

    ViewPager mPager;
    static ArbaeenHelper helper ;
	static Cursor cursor ;
	static Context context ;
	static int i = 0 ;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Sherlock___Theme_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this ;
        helper = new ArbaeenHelper(this);
       
        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.next);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(mPager.getCurrentItem()+1);
            }
        });
        button = (Button)findViewById(R.id.prev);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(mPager.getCurrentItem()-1);
            }
        });
        
        button = (Button)findViewById(R.id.first);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });
        button = (Button)findViewById(R.id.last);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(42);
            }
        });
        try {
            helper.createDataBase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
         Toast.makeText(this, "Err"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
	}
	
	
	
	
	public static class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 42;
        }

        @Override
        public Fragment getItem(int position) {
            //return ArrayListFragment.newInstance(position);
        	return HadeethFragment.newInstance(position);
        }
    }
	public static class HadeethFragment extends SherlockFragment {
		TextView t,title ;
		
		public static HadeethFragment newInstance (int position){
			HadeethFragment hf = new HadeethFragment();
			Bundle args = new Bundle();
			args.putInt("pos", position);
			hf.setArguments(args);
			return hf;
		}
		
		

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			Toast.makeText(context, "OnCreate Called", Toast.LENGTH_SHORT).show();
		}



		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
		}



		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View view = inflater.inflate(R.layout.hadeeth,container,false);
			t = (TextView)view.findViewById(R.id.mtn);
			title = (TextView)view.findViewById(R.id.text);
			int pos = getArguments().getInt("pos");			
			//if(pos==0)pos++;
			if(pos!=0){
			title.setText("Hadeeth "+pos);
			cursor = helper.getHadeethById(""+pos);		
			if(cursor.moveToFirst()){
				do{
					t.setText(cursor.getString(1));
					Toast.makeText(context, "inside while"+i, Toast.LENGTH_SHORT).show();
					i++;
				}while(cursor.moveToNext());
			}
			}
			else{
				t.setText(view.getResources().getText(R.string.intro));
			}
			
			
			
			return view;
		}
		
	}
	
	
	public static class ArrayListFragment extends SherlockListFragment {
        int mNum;

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static ArrayListFragment newInstance(int num) {
            ArrayListFragment f = new ArrayListFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.arbaeen_pager_list, container, false);
            View tv = v.findViewById(R.id.text);
            ((TextView)tv).setText("Fragment #" + mNum);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
        	String s[] = {"dddddlkdfjgkldjfglkjdfgkjdflgkjdfglkdjgflkdkfjlgdlfkgjdfgjdflkgjdflkgjdflkgjldfkgjdlfkgjdlfkgjdlkfgjdlkfgjdlkfgjldfgjldkfgjldkgjdlkfgjldkfgjldkgjldkfgjdlkfgjdlkgjdlkgjdlkfgjdlkfgjdlfkgjdlkfgjdlkfgjdlkgjdlkgjdlkgjdlkgjldkjglkdfgjldkfjglkdfgjldkjgldjgldfjgldkfjgljdlkgjdlfkgjldfjglkdfjglkdfjlkjgdfl"};
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, s));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + id);
        }
    }

}
