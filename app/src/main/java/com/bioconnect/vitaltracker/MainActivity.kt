package com.bioconnect.vitaltracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.bioconnect.vitaltracker.ui.theme.BioconnectTheme

import com.example.bioconnect.MeasureView
import com.example.bioconnect.data.HealthData

// option
import com.example.bioconnect.utils.ESTIMATE_TIME
import com.example.bioconnect.utils.GET_LOG
import com.example.bioconnect.utils.stressToLevel
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BioconnectTheme {
                // 측정 결과 변수
                val result = remember{
                    MutableStateFlow(
                        Pair<Boolean, HealthData?>(false, null)
                    )
                }

                // setting estimate time : Int (default : 15)
                ESTIMATE_TIME = 15

                // setting communication log flag : Boolean
                GET_LOG = true

                // 측정 결과 리스너
                LaunchedEffect(key1 = result.collectAsState().value){
                    val resultOk = result.value.first
                    Log.e("Test>>", "resultOk: ${resultOk}")

                    if (resultOk){
                        Log.e("Test>>", "result: ${result.value.second}")
                        Log.e("Test>>", "bpm reulst: ${result.value.second!!.bpm}")
                        // stress index to stress level
                        Log.e("Test>>", stressToLevel(result.value.second!!.stress))
                    }
                }

                MeasureView(
                    activity = this@MainActivity,
                    baseUrl = "http://118.128.153.171:8088", //측정지표 분석 서버 URL
                    showResultTable = true,
                    result = result
                )
            }
        }
    }
}