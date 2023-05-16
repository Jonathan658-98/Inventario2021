package com.ejemplo.inventario2021.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ejemplo.inventario2021.actividades.ActivityIngresarProductos;
import com.ejemplo.inventario2021.R;
import com.ejemplo.inventario2021.actividades.ImprimirActivity;
import com.ejemplo.inventario2021.actividades.InformeFacturasActivity;
import com.ejemplo.inventario2021.actividades.ListaProductosRecyclerActivity;
import com.ejemplo.inventario2021.actividades.ListaProductosVentaActivity;
import com.ejemplo.inventario2021.actividades.ListaVentasActivity;
import com.ejemplo.inventario2021.interfaces.IComunicaFragments;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    MenuItem fav;

    View vista;
    Activity actividad;
    CardView cardIngresar,cardFacturas, cardListar, cardVenta;
    Button venta;
    IComunicaFragments interfaceComunicaFragments;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //==============================================================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    /*public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        fav = menu.add("add");
        fav.setIcon(R.drawable.inventario);
    }*/


    //==============================================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //Establecer la comunicación
        vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        //Instanciar el cardview y establece la comunicacion con la UI
        cardIngresar = vista.findViewById(R.id.cardIngresar);
        cardListar = vista.findViewById(R.id.cardListar);
        cardVenta = vista.findViewById(R.id.cardVenta);
        cardFacturas = vista.findViewById(R.id.cardFacturas);
        venta = vista.findViewById(R.id.ventaProductos);

        //Evento para el cardIngresarProductos
        cardIngresar.setOnClickListener(new View.OnClickListener() {    //Evento click
            @Override
            public void onClick(View v) {
                //Fragments -> getContext() || Activity -> getApplicationContext()
                Intent intent = new Intent(getContext(), ActivityIngresarProductos.class);
                startActivity(intent);
                //Toast.makeText(getContext(),"Ingresar Productos desde el fragment", Toast.LENGTH_SHORT).show();
            }
        });

        //Evento para el cardListar
        cardListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"IMPRIMIR Productos desde el fragment", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ListaProductosRecyclerActivity.class);
                startActivity(intent);
            }
        });

        //Evento para el cardVenta
        cardVenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getContext(), ListaProductosVentaActivity.class);
                startActivity(intent);
            }
        });

        //Evento para el cardImprimir
        cardFacturas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Intent intent = new Intent(getContext(), ImprimirActivity.class);
                Intent intent = new Intent(getContext(), InformeFacturasActivity.class);
                startActivity(intent);
            }
        });

        venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListaVentasActivity.class);
                startActivity(intent);
            }
        });

        return vista;   //Devuele la vista
    }

    //==============================================================================================

}