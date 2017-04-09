package fr.jufab.springboot;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringApplication;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringApplication.class)
public class AbsencesApplicationMockTest {

    @Test
    public void testDuDemarrageDeLApplication() {
        //given
        String[] args = new String[]{};
        PowerMockito.mockStatic(SpringApplication.class);
        BDDMockito.given(SpringApplication.run(AbsencesApplication.class,args)).willReturn(null);
        //when
        AbsencesApplication.main(args);
        //then
        PowerMockito.verifyStatic();
        SpringApplication.run(AbsencesApplication.class,args);
    }

}
