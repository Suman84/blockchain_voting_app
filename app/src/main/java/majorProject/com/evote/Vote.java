package majorProject.com.evote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Provider;
import java.security.Security;

public class Vote extends AppCompatActivity {

    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(67219750L);
    private final static BigInteger GAS_PRICE = BigInteger.valueOf(200000L);
    private final static BigInteger candidateNumber = BigInteger.valueOf(0);
    private final static String CONTRACT_ADDRESS = "0xe526aE20aeF33EF4E58d4607dCf616F7ee980e84";
    private final static String ADMIN_PRIVATE_KEY = "61259ca54f9fbed5336bd00795124c90e9f87b7c37f6bc0fdb6a3f41d8610379";

    private Web3j web3;
    //FIXME: Add your own password here
    private final String password = "medium";
    private String fileName;
    private String walletPath;
    private File walletDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        setupBouncyCastle();
        walletPath = getFilesDir().getAbsolutePath();

    }

    public void connectToEthNetwork(View v) {
        toastAsync("Connecting to Ethereum network...");
        // FIXME: Add your own API key here
        web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/63feaecec18842dbbc672f7761af31d0"));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            Credentials credentials = Credentials.create(ADMIN_PRIVATE_KEY);
//            deployContract(web3,credentials);
            toastAsync("Voting .. please wait!");

            Election election2 = Election.load(CONTRACT_ADDRESS, web3, credentials, GAS_LIMIT, GAS_PRICE);


            election2.vote(candidateNumber).sendAsync().get();
 //           RemoteCall<Tuple2<String, BigInteger>> result2 = election2.candidates(candidateNumber);
//             String stringname = result2.send().getValue1();


//            Log.d("nvm", "connectToEthNetwork: "+ stringname);


            if(!clientVersion.hasError()){
                toastAsync("Voting Successful!");
            }
            else {
                toastAsync(clientVersion.getError().getMessage());
            }
        } catch (Exception e) {
            Log.d("error",e.getMessage());
            toastAsync(e.getMessage());
        }
    }


    private String deployContract(Web3j web3j, Credentials credentials) throws Exception {
        return Election.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT, "PM election")
                .send()
                .getContractAddress();
    }

    public void createWallet(View v){

        try{
             fileName =  WalletUtils.generateLightNewWalletFile(password,walletDir);
            walletDir = new File(walletPath + "/" + "test");
            toastAsync("Wallet generated");
        }
        catch (Exception e){
            toastAsync(e.getMessage());
        }
    }

    public void getAddress(View v){
        try {
            Credentials credentials = WalletUtils.loadCredentials(password, walletDir);
            toastAsync("Your address is " + credentials.getAddress());
        }
        catch (Exception e){
            toastAsync(e.getMessage());
        }
    }

    public void sendTransaction(View v){
        try{
            Credentials credentials = WalletUtils.loadCredentials(password, walletDir);
            TransactionReceipt receipt = Transfer.sendFunds(web3,credentials,"0x31B98D14007bDEe637298086988A0bBd31184523",new BigDecimal(1),Convert.Unit.ETHER).sendAsync().get();
            toastAsync("Transaction complete: " +receipt.getTransactionHash());
        }
        catch (Exception e){
            toastAsync(e.getMessage());
        }
    }


    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }

    // Workaround for bug with ECDA signatures.
    private void setupBouncyCastle() {
      final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
      if (provider == null) {
        // Web3j will set up the provider lazily when it's first used.
        return;
      }
      if (provider.getClass().equals(BouncyCastleProvider.class)) {
        // BC with same package name, shouldn't happen in real life.
        return;
      }
      // Android registers its own BC provider. As it might be outdated and might not include
      // all needed ciphers, we substitute it with a known BC bundled in the app.
      // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
      // of that it's possible to have another BC implementation loaded in VM.
      Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
      Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }
}
