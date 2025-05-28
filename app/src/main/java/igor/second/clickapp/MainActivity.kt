package igor.second.clickapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import igor.second.clickapp.ui.theme.ClickAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val purchaseHelper = PurchaseHelper(this)
        purchaseHelper.billingSetup()

        val dataStoreManager = DataStoreManager(this)

        setContent {
            ClickAppTheme {

                var userSpeedValue = remember { mutableIntStateOf(10) }
                var userRealCoins = remember { mutableStateOf("0") }
                var userCoinValue = remember { mutableFloatStateOf(0f) }

                var enabledDialog = remember { mutableStateOf(false) }
                var enabledDialogPurchase = remember { mutableStateOf(false) }

                LaunchedEffect(true) {
                    dataStoreManager.getSettings().collect { settings ->
                        userSpeedValue.intValue = settings.userSpeedValue
                        userCoinValue.floatValue = settings.userCoinValue
                        userRealCoins.value = settings.userRealCoins
                    }
                }
                MainFunForClick(
                    purchaseHelper = purchaseHelper,
                    dataStoreManager = dataStoreManager,
                    userCoinValue = userCoinValue,
                    userSpeedValue = userSpeedValue,
                    enabledDialog = enabledDialog,
                    userRealCoins = userRealCoins,
                    enabledDialogPurchase = enabledDialogPurchase
                )
            }
        }
    }
}

/*@Composable
fun TestPurchases(
    purchaseHelper: PurchaseHelper,
    modifier: Modifier = Modifier,
    dataStoreManager: DataStoreManager,
    cardScoreValue: MutableState<Int>,
    userValue: MutableState<Float>
){

    val buyEnabled by purchaseHelper.buyEnabled.collectAsState(false)
    val consumeEnabled by purchaseHelper.consumeEnabled.collectAsState(false)
    val productName by purchaseHelper.productName.collectAsState("")
    val statusText by purchaseHelper.statusText.collectAsState("")

    val scope = rememberCoroutineScope()

    Column(
        Modifier.padding(20.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(productName, Modifier.padding(20.dp), fontSize = 30.sp)
        Text(statusText)
        Text(cardScoreValue.value.toString())
        Row(Modifier.padding(20.dp)) {
            Button(onClick = { purchaseHelper.makePurchase() }, Modifier.padding(20.dp),
                enabled = buyEnabled
            ) {
                Text("Purchase")
            }
            Button(
                onClick = { purchaseHelper.consumePurchase() },
                Modifier.padding(20.dp),
                enabled = consumeEnabled
            ) {
                Text("Consume")
            }
        }
    }
    if (statusText == "Purchase Completed"){
        LaunchedEffect(true) {
            scope.launch {
                cardScoreValue.value += 1
                dataStoreManager.saveSettings(
                    SettingData(
                        cardScoreValue = cardScoreValue.value,
                        userValue = userValue.value
                    )
                )
            }
        }
    } else if (statusText == "Purchase Consumed"){
        LaunchedEffect(true) {
            scope.launch {
                cardScoreValue.value -= 1
                dataStoreManager.saveSettings(
                    SettingData(
                        cardScoreValue = cardScoreValue.value,
                        userValue = userValue.value
                    )
                )
            }
        }
    }
}*/