package igor.second.clickapp

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch

@Composable
fun MainFunForClick(
    modifier: Modifier = Modifier,
    purchaseHelper: PurchaseHelper,
    userSpeedValue: MutableState<Int>,
    userCoinValue: MutableState<Float>,
    dataStoreManager: DataStoreManager,
    enabledDialog: MutableState<Boolean>,
    enabledDialogPurchase: MutableState<Boolean>,
    userRealCoins: MutableState<String>
){

    var scope = rememberCoroutineScope()

    val consumeEnabled by purchaseHelper.consumeEnabled.collectAsState(false)

    Box(modifier = modifier){

        Image(
            painter = painterResource(R.drawable.clickfon),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier.fillMaxSize()
        )

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ){
            Row (
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp)
            ) {
                Card(
                modifier = modifier
                    .size(144.dp)
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(64.dp, 16.dp, 64.dp, 16.dp))
                ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.onBackground)
                    ) {
                    Button(
                        colors = ButtonColors(
                            contentColor = MaterialTheme.colorScheme.background,
                            containerColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = Color.Gray,
                            disabledContainerColor = Color.Black
                        ),
                        onClick = { enabledDialogPurchase.value = true }) {
                        Text(stringResource(R.string.upgrade)) }
                    }
                }
                Card(
                    modifier = modifier
                        .size(144.dp)
                        .padding(8.dp)
                        .clip(shape = RoundedCornerShape(16.dp, 64.dp, 16.dp, 64.dp))
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.onBackground)
                    ) {
                        Button(
                            onClick = {enabledDialog.value = !enabledDialog.value}
                        ) { Text(stringResource(R.string.settings)) }
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 64.dp, top = 180.dp)
            ) {
                Text(userSpeedValue.value.toString(), color = Color.White)
                Text(
                    userCoinValue.value.toString(),
                    fontSize = 36.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                HorizontalDivider(
                    thickness = 3.dp,
                    color = Color.LightGray,
                    modifier = modifier
                        .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
                )
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(modifier.fillMaxSize()) {
                        Column (
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 40.dp)
                        ) {
                            CircularProgressIndicator(
                                progress = { userSpeedValue.value.toFloat() / 1000 },
                                modifier = Modifier.size(360.dp),
                                color = Color.Green,
                                trackColor = Color.Red,
                                strokeWidth = 8.dp,
                            )
                        }
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Card(
                                shape = CircleShape,
                                modifier = modifier
                                    .size(200.dp)
                                    .clickable(onClick = {
                                        userCoinValue.value += userSpeedValue.value
                                        scope.launch {
                                            dataStoreManager.saveSettings(
                                                SettingData(
                                                    userSpeedValue = userSpeedValue.value,
                                                    userCoinValue = userCoinValue.value,
                                                    userRealCoins = userRealCoins.value
                                                )
                                            )
                                        }
                                    })
                            ) {
                                Column(
                                    modifier = modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.coin),
                                        contentScale = ContentScale.FillBounds,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        if (enabledDialog.value){
            DialogSettingApp(
                enabledDialog = enabledDialog,
                dataStoreManager = dataStoreManager,
                userRealCoins = userRealCoins,
                userSpeedValue = userSpeedValue,
                userCoinValue = userCoinValue
            )
        }
        if (enabledDialogPurchase.value){
            DialogForPurchaseApp(
                purchaseHelper = purchaseHelper,
                enabledDialogPurchase = enabledDialogPurchase
            )
        }
        if (consumeEnabled){
            ConsumePurchaseDialog(
                purchaseHelper = purchaseHelper,
                userSpeedValue = userSpeedValue,
                userCoinValue = userCoinValue,
                userRealCoins = userRealCoins,
                dataStoreManager = dataStoreManager
            )
        }
    }
}

@Composable
fun ConsumePurchaseDialog(
    purchaseHelper: PurchaseHelper,
    userSpeedValue: MutableState<Int>,
    userRealCoins: MutableState<String>,
    userCoinValue: MutableState<Float>,
    dataStoreManager: DataStoreManager
){
    val consumeEnabled by purchaseHelper.consumeEnabled.collectAsState(false)
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = {consumeEnabled == false}) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ){
                Text(stringResource(R.string.purchase_completed))
                IconButton(
                    enabled = consumeEnabled,
                    onClick = {
                    userSpeedValue.value += 10
                    purchaseHelper.consumePurchase()
                    scope.launch {
                        dataStoreManager.saveSettings(
                            SettingData(
                                userSpeedValue = userSpeedValue.value,
                                userCoinValue = userCoinValue.value,
                                userRealCoins = userRealCoins.value
                            )
                        )
                    }
                }) {
                    Icon(
                        Icons.Rounded.Done,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun DialogForPurchaseApp(
    enabledDialogPurchase: MutableState<Boolean>,
    purchaseHelper: PurchaseHelper
){

    var enabledPurchase by remember { mutableStateOf(false) }
    val buyEnabled by purchaseHelper.buyEnabled.collectAsState(false)
    var userHeight = if (enabledPurchase){ 0.1f } else { 0.2f }

    Dialog(onDismissRequest = {}){
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(userHeight)
        ) {
            if (enabledPurchase){
                Column (
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ){
                    Text(
                        stringResource(R.string.purchase_in_progress),
                        modifier = Modifier.padding(8.dp)
                    )
                    IconButton(
                        enabled = true,
                        onClick = {
                            enabledPurchase = false
                            enabledDialogPurchase.value = false
                        }) {
                        Icon(
                            Icons.Rounded.Close,
                            contentDescription = null
                        )
                    }
                }
            } else {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray)
                ){
                    Text(
                        stringResource(R.string.purchase_info),
                        fontSize = 24.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    ) {
                        IconButton(
                            onClick = {
                            enabledDialogPurchase.value = false
                        }) {
                            Icon(
                                Icons.Rounded.Close,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        IconButton(
                            enabled = buyEnabled,
                            onClick = {
                            purchaseHelper.makePurchase()
                            enabledPurchase = true
                        }) {
                            Icon(
                                Icons.Rounded.Done,
                                contentDescription = null,
                                tint = Color.Green,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DialogSettingApp(
    enabledDialog: MutableState<Boolean>,
    userRealCoins: MutableState<String>,
    userCoinValue: MutableState<Float>,
    userSpeedValue: MutableState<Int>,
    dataStoreManager: DataStoreManager
){

    var courseScoreFromRealCoin by remember { mutableIntStateOf(1000000) }
    var courseCoinFromMoney by remember { mutableFloatStateOf(0.003423f) }
    var textFieldValue by remember { mutableStateOf("0") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = {enabledDialog.value = false}){

        Card (
            modifier = Modifier
                .fillMaxHeight(0.5f)
        ) {
            Box(modifier = Modifier) {

                Column (
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray)
                ){
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        IconButton(
                            onClick = { enabledDialog.value = false }) {
                            Icon(
                                Icons.Rounded.Close,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                    Text(
                        "${stringResource(R.string.chameleon)}: ${userRealCoins.value}",
                        fontSize = 32.sp,
                        color = Color.Black
                    )
                    Text(
                        "${stringResource(R.string.coins)}: ${userCoinValue.value}",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Column (
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            "1 chameleon = $courseScoreFromRealCoin ${stringResource(R.string.coins)}",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            "1 chameleon = $courseCoinFromMoney $",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Button(
                            modifier = Modifier.padding(top = 32.dp),
                            onClick = {
                                if (userCoinValue.value.toInt() > courseScoreFromRealCoin){
                                    var a = userCoinValue.value.toInt() / courseScoreFromRealCoin.toInt()
                                    userRealCoins.value = (userRealCoins.value.toInt() + a).toString()
                                    userCoinValue.value = userCoinValue.value - a * courseScoreFromRealCoin
                                    textFieldValue = "0"
                                    scope.launch{
                                        dataStoreManager.saveSettings(
                                            SettingData(
                                                userSpeedValue = userSpeedValue.value,
                                                userCoinValue = userCoinValue.value,
                                                userRealCoins = userRealCoins.value
                                            )
                                        )
                                    }
                                } else {
                                    Toast.makeText(context, R.string.not_coins,
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) { Text(stringResource(R.string.change)) }
                        PrivacyPolicy(R.string.privacy_policy)
                    }
                }
            }
        }
    }
}

/*@Composable
fun TestCounter(){
    var enabledOfAutoClick by remember { mutableStateOf(false) }
    var enabledOfAutoClickPro by remember { mutableStateOf(false) }
    var enabledOfAutoClickProExtra by remember { mutableStateOf(false) }
    var enabledOfAutoClickProExtraNew by remember { mutableStateOf(false) }

    if (enabledOfAutoClick ||
        enabledOfAutoClickPro ||
        enabledOfAutoClickProExtraNew ||
        enabledOfAutoClickProExtra
    ){
        LaunchedEffect (key1 = true) {
            launch {
                while (enabledOfAutoClick == true) {
                    userCoinValue.value += userSpeedValue.value
                    delay(1000)
                }
            }
            launch {
                while (enabledOfAutoClickPro == true) {
                    userCoinValue.value += userSpeedValue.value
                    delay(100)
                }
            }
            launch {
                while (enabledOfAutoClickProExtra == true) {
                    userCoinValue.value += userSpeedValue.value
                    delay(10)
                }
            }
            launch {
                while (enabledOfAutoClickProExtraNew == true) {
                    userCoinValue.value += userSpeedValue.value
                    delay(1)
                }
            }
        }
    }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
        ) {
            Switch(
                checked = enabledOfAutoClick,
                onCheckedChange = {
                    enabledOfAutoClick = !enabledOfAutoClick
                }
            )
            Switch(
                checked = enabledOfAutoClickPro,
                onCheckedChange = {
                    enabledOfAutoClickPro = !enabledOfAutoClickPro
                }
            )
            Switch(
                checked = enabledOfAutoClickProExtra,
                onCheckedChange = {
                    enabledOfAutoClickProExtra = !enabledOfAutoClickProExtra
                }
            )
            Switch(
                checked = enabledOfAutoClickProExtraNew,
                onCheckedChange = {
                    enabledOfAutoClickProExtraNew = !enabledOfAutoClickProExtraNew
                }
            )
        }
    }
}*/