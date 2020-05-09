package org.neo4j.ogm.demo.osgi.itest;

import org.apache.karaf.itests.KarafTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.karaf.options.KarafDistributionOption;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.Bundle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ExampleITest extends KarafTestSupport {

    @Configuration
    public Option[] config() {
        List<Option> config = new LinkedList(Arrays.asList(super.config()));
        config.add(KarafDistributionOption.editConfigurationFilePut("etc/config.properties", "karaf.framework", "equinox"));
        return config.toArray(new Option[config.size()]);
    }

    @Test
    public void listBundleCommand() throws Exception {
        addFeaturesRepository("mvn:org.neo4j/neo4j-ogm-demo-osgi-feature/1.0.0/xml/features");
        installAndAssertFeature("neo4j-ogm-demo-osgi-feature");

        Bundle bundle = findBundleByName("org.neo4j.ogm-demo-osgi-client");
        Assert.assertEquals(Bundle.ACTIVE, bundle.getState());
    }

}