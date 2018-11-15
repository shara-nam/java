package templates;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.nio.charset.Charset;
import java.util.*;

@Configuration
public class WebServiceEntityConfig {

    private static final String WS_PACKAGE_PATH_ITEM = "com.csdm.newsfeed.ws.item";

    private static final String JAXB_FORMATTED_OUTPUT = "jaxb.formatted.output";

    @Value("${null}")
    private String serviceUser;

    @Value("${null}")
    private String servicePassword;

    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.port}")
    private Integer proxyPort;

    @Bean
    public WebServiceTemplate webServiceTemplateItem() {
        final Jaxb2Marshaller marshaller = getJaxb2MarshallerItem();
        return getWebServiceTemplate(marshaller);
    }

    private Jaxb2Marshaller getJaxb2MarshallerItem() {
        return getJaxb2MarshallerForPath(WS_PACKAGE_PATH_ITEM);
    }

    private Jaxb2Marshaller getJaxb2MarshallerForPath(String path) {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan(path);
        Map<String, Object> map = new HashMap<>();
        map.put(JAXB_FORMATTED_OUTPUT, true);
        jaxb2Marshaller.setMarshallerProperties(map);
        return jaxb2Marshaller;
    }

    private WebServiceTemplate getWebServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        webServiceTemplate.setMessageSender(createHttpClient());
        return webServiceTemplate;
    }

    @Bean
    public WebServiceMessageSender createHttpClient() {
        RequestConfig.Builder builder = RequestConfig.custom().setSocketTimeout(600000) // SocketTimeout
                .setConnectTimeout(180000);

        if ((proxyHost != null && !proxyHost.isEmpty()) && (proxyPort != null && proxyPort != 0)) {
            builder.setProxy(new HttpHost(proxyHost, proxyPort));
        }

        RequestConfig requestConfig = builder.build();

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultHeaders(createAuthHeader())
                .setDefaultRequestConfig(requestConfig)
                .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                .setSSLSocketFactory(getSslTrustFactory()).build();

        return new HttpComponentsMessageSender(httpClient);
    }

    private Collection<? extends Header> createAuthHeader() {
        String auth = serviceUser + ":" + servicePassword;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);

        Header header = new BasicHeader("Authorization", authHeader);
        List<Header> headers = new ArrayList<>();
        headers.add(header);

        return headers;
    }

    /**
     * Create a Trust Factory for Self-Signed Certificates. Only for internal connections use!
     *
     * @return LayeredConnectionSocketFactory
     */
    private LayeredConnectionSocketFactory getSslTrustFactory() {
        SSLContextBuilder builder = new SSLContextBuilder();
        SSLConnectionSocketFactory sslsf = null;
        try {
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            sslsf = new SSLConnectionSocketFactory(builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sslsf;
    }
}
