package com.example.luisfernandomedinallorenti.jcalendario;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewEventos extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_eventos);

        listView=(ListView)findViewById(R.id.listEventos);
        listView.setOnItemLongClickListener(this);

        Bundle bundle=getIntent().getExtras();
        int dia=bundle.getInt("dayOfMoth");
        int mes=bundle.getInt("moth");
        int anio=bundle.getInt("year");
        String cadena=dia+" - "+mes +" - "+anio;
        BDSqlite bd=new BDSqlite(getApplicationContext(),"Agenda",null,1);
        db=bd.getReadableDatabase();

        String sql="select * from eventos where fecha='"+cadena+"'";
        Cursor cursor;
        String fecha,nombre,descripcion,tipo;
        try {
            cursor=db.rawQuery(sql,null);
            arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
            if (cursor.moveToFirst()){
                do {
                    fecha=cursor.getString(1);
                    nombre=cursor.getString(2);
                    descripcion=cursor.getString(3);
                    tipo=cursor.getString(4);
                    arrayAdapter.add(fecha+","+nombre+","+descripcion+","+tipo);
                }while (cursor.moveToNext());
                listView.setAdapter(arrayAdapter);
            }else {
                Toast.makeText(getApplication(),"No hay datos",Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }catch (Exception e){
            Toast.makeText(getApplication(),"Error: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    private void eliminar(String dato){
        String []datos=dato.split(", ");
        String sql="delete from eventos where fecha='"+datos[0]+"' and" +
                " nombre='"+datos[1]+"' and descripcion='"+datos[2]+"' and tipo='"+datos[3]+"'";
        try {
            arrayAdapter.remove(dato);
            listView.setAdapter(arrayAdapter);
            db.execSQL(sql);
            Toast.makeText(getApplication(),"Evento Eliminado",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplication(),"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        CharSequence []items=new CharSequence[2];
        items[0]="Eliminar Evento";
        items[1]="Cancelar";
        builder.setTitle("Eliminar evento").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    //Eliminar Evento
                    eliminar(parent.getItemAtPosition(which).toString());
                }
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
        return false;
    }
}
