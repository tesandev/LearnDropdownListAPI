package com.tesan.learndropdownlistapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.tesan.learndropdownlistapi.model.ResponseKecamatan
import com.tesan.learndropdownlistapi.model.ResponseKelurahan
import com.tesan.learndropdownlistapi.model.ResponseKota
import com.tesan.learndropdownlistapi.model.ResponseProvinsi
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    //provinsi
    private var listIdprovinsi = ArrayList<Int>()
    private var listNamaprovinsi = ArrayList<String>()

    //kota
    private var listIdkota = ArrayList<Int>()
    private var listNamaKota = ArrayList<String>()

    //kecamtan
    private var listIdKecamatan = ArrayList<Int>()
    private var listNamaKecamatan = ArrayList<String>()

    //kecamtan
    private var listIdKelurahan = ArrayList<Long>()
    private var listNamaKelurahan = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showProvinsi()
    }

    private fun showProvinsi() {
        ApiConfig.instance.getProvinsi().enqueue(object :Callback<ResponseProvinsi>{
            override fun onResponse(
                call: Call<ResponseProvinsi>,
                response: Response<ResponseProvinsi>
            ) {
                val resp = response.body()?.provinsi
                resp?.forEach {
                    listIdprovinsi.add(it.id)
                    listNamaprovinsi.add(it.nama)
                }

                spProvinsi.onItemSelectedListener = this@MainActivity
                val adapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,listNamaprovinsi)
                spProvinsi.adapter = adapter

                Toast.makeText(this@MainActivity,"yes",Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseProvinsi>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun showKota(id_prov: Int) {
        Log.d("cek",id_prov.toString())
        ApiConfig.instance.getKota(id_prov).enqueue(object :Callback<ResponseKota>{
            override fun onResponse(call: Call<ResponseKota>, response: Response<ResponseKota>) {
                val listresp = response.body()?.kotaKabupaten

                listIdkota.clear()
                listNamaKota.clear()

                listresp?.forEach {
                    listIdkota.add(it.id)
                    listNamaKota.add(it.nama)
                }

                spKota.onItemSelectedListener = this@MainActivity
                val adapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,listNamaKota)
                spKota.adapter = adapter
            }

            override fun onFailure(call: Call<ResponseKota>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showKecamatan(idKota: Int) {
        ApiConfig.instance.getKecamatan(idKota).enqueue(object :Callback<ResponseKecamatan>{
            override fun onResponse(call: Call<ResponseKecamatan>, response: Response<ResponseKecamatan>) {
                val listresp = response.body()?.kecamatan

                listIdKecamatan.clear()
                listNamaKecamatan.clear()

                listresp?.forEach {
                    listIdKecamatan.add(it.id)
                    listNamaKecamatan.add(it.nama)
                }

                spKecamatan.onItemSelectedListener = this@MainActivity
                val adapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,listNamaKecamatan)
                spKecamatan.adapter = adapter
            }

            override fun onFailure(call: Call<ResponseKecamatan>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        p0?.getItemAtPosition(p2)
        if(p0?.selectedItem == spProvinsi.selectedItem){
            showKota(listIdprovinsi[p2])
        }else if(p0?.selectedItem == spKota.selectedItem){
            showKecamatan(listIdkota[p2])
        }else if(p0?.selectedItem == spKecamatan.selectedItem){
            showKelurahan(listIdKecamatan[p2])
        }
    }

    private fun showKelurahan(idKecamatan: Int) {
        ApiConfig.instance.getKelurahan(idKecamatan).enqueue(object :Callback<ResponseKelurahan>{
            override fun onResponse(
                call: Call<ResponseKelurahan>,
                response: Response<ResponseKelurahan>
            ) {
                val res = response.body()?.kelurahan

                listIdKelurahan.clear()
                listNamaKelurahan.clear()

                res?.forEach {
                    listIdKelurahan.add(it.id)
                    listNamaKelurahan.add(it.nama)
                }
                spKelurahan.onItemSelectedListener = this@MainActivity
                val adpt = ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,listNamaKelurahan)
                spKelurahan.adapter = adpt
            }

            override fun onFailure(call: Call<ResponseKelurahan>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}