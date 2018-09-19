package com.realdolmen.course;

import org.junit.BeforeClass;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

import static org.junit.Assume.assumeTrue;

public abstract class AbstractRemoteIntegrationTest {
    protected static InitialContext context;

    @BeforeClass
    public static void initializeJndiContext() throws Exception {
        context = new InitialContext(jdniProperties());
    }

    private static Hashtable<String, Object> jdniProperties() {
        Hashtable<String, Object> properties = new Hashtable<>();
        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        properties.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
        properties.put("jboss.naming.client.ejb.context", true);
        return properties;
    }

    @SuppressWarnings("unchecked")
    protected static <T> T lookup(String jndiString) throws NamingException {
        try {
            return (T) context.lookup(jndiString);
        } catch(CommunicationException e) {
            throw new RuntimeException("Unable to perform JNDI lookup. Did you start up your application server? Is it running the latest update (redeploy if necessary)?", e);
        }
    }
}
