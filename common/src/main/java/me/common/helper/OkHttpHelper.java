package me.common.helper;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.Proxy;
import java.security.cert.X509Certificate;

public class OkHttpHelper {

    /**
     * Returns an OkHttpClient that ignores SSL certificate validation.
     * @return OkHttpClient
     */
    public static OkHttpClient getUnsafeOkHttpClient() {
        return getUnsafeOkHttpClient(null);
    }

    public static OkHttpClient getUnsafeOkHttpClient(Interceptor interceptor) {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create a ssl socket factory with our all-trusting manager
            OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
            newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
            newBuilder.hostnameVerifier((hostname, session) -> true);

            // Add interceptor
            if (interceptor != null) {
                newBuilder.addInterceptor(interceptor);
            }

            return newBuilder.proxy(Proxy.NO_PROXY).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
