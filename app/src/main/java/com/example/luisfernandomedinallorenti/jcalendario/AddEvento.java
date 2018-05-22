package com.example.luisfernandomedinallorenti.jcalendario;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddEvento extends AppCompatActivity implements View.OnClickListener {
    private EditText nombreEvento,descripcion;
    private TextView fecha;
    private Spinner tipo;
    private Button guardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evento);

        nombreEvento=(EditText)findViewById(R.id.editNombreEvento);
        descripcion=(EditText)findViewById(R.id.editDescripcionEvento);
        fecha=(TextView)findViewById(R.id.textFecha);
        tipo=(Spinner) findViewById(R.id.spnTipo);
        String[]lista={"Aniversario","Personal","Trabajo","Tarea"};
        tipo.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lista));

        Bundle bundle=getIntent().getExtras();
        int dia=bundle.getInt("dayOfMoth");
        int mes=bundle.getInt("moth");
        int anio=bundle.getInt("year");

        fecha.setText(dia+" - "+mes+" - "+anio);

        guardar=(Button)findViewById(R.id.btnListo);
        guardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==guardar.getId()){
            //Guardar datos de Eventos
            BDSqlite bd=new BDSqlite(getApplication(),"Agenda",null,1);
            SQLiteDatabase basedatos=bd.getWritableDatabase();
            String sql="insert into eventos ("+
                    "fecha,nombre,descripcion,tipo,hraNotificacion) values('"+
                    fecha.getText()+
                    "','"+nombreEvento.getText()+
                    "','"+descripcion.getText()+
                    "','"+tipo.getSelectedItem()+
                    "',NULL)";
            try{
                basedatos.execSQL(sql);
                nombreEvento.setText("");
                descripcion.setText("");
                Toast.makeText(getApplication(),"Datos guardados",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(getApplication(),"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            this.finish();
        }else {
            this.finish();
            return;
        }
    }
}
