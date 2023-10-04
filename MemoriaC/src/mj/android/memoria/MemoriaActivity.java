package mj.android.memoria;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MemoriaActivity extends Activity {
	
	private GridView mGrid;
	private GridAdapter mAdapter;
	
	int GRID_SIZE = 6;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mGrid = (GridView)findViewById(R.id.field);
        mGrid.setNumColumns(GRID_SIZE);
        mGrid.setEnabled(true);
        
        mAdapter = new GridAdapter(this, GRID_SIZE, GRID_SIZE);
        mGrid.setAdapter(mAdapter);
        
        mGrid.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
            	
        		mAdapter.checkOpenCells ();
        		mAdapter.openCell (position);
        		
            	if (mAdapter.checkGameOver())
            		Toast.makeText (getApplicationContext(), "Игра закончена", Toast.LENGTH_SHORT).show(); 
              }
          });
    }
    
}