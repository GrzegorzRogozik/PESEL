import com.uam.Pesel;
import org.junit.Assert;
import org.junit.Test;

public class Tests {
    @Test
    public void peselWithLenghtotherthenElevenShouldAlweysReturnFalse()
    {
        Pesel tester = new Pesel();
        System.out.println("First testcase executed");
        Assert.assertEquals(tester.peselValidation("123123"),false);
        Assert.assertEquals(tester.peselValidation("123456789101"),false);
        Assert.assertEquals(tester.peselValidation("87090409472"),true);

    }
// Nr PESEL uwzględnia osoby urodzone tylko między 1900, a 2100 rokiem, wyszedłem z założenia, że na chwile obecną jest to wystarczajaca grupa numerów
    @Test
    public void peselWithWrongMonthNumberSchouldAleysReturnFalse()
    {
        Pesel tester = new Pesel();
        System.out.println("Second testcase executed");
        Assert.assertEquals(tester.peselValidation("01431951295"),false);
        Assert.assertEquals(tester.peselValidation("78002642936"),false);
        Assert.assertEquals(tester.peselValidation("64032864469"),true);
    }

    @Test
    public void nameWithNumberOrSpecialCharShouldAlwaysReturnFalse()
    {
        Pesel tester = new Pesel();
        System.out.println("Third testcase executed");
        Assert.assertEquals(tester.nameValidator("!@sasdSASD"),false);
        Assert.assertEquals(tester.nameValidator("M4rek"),false);
        Assert.assertEquals(tester.nameValidator("Damian"),true);
        Assert.assertEquals(tester.nameValidator("AnDrZej"),true);
    }

    @Test
    public void cityWithNumberOrSpecialCharShouldAlwaysReturnFalse()
    {
        Pesel tester = new Pesel();
        System.out.println("Another testcase executed");
        Assert.assertEquals(tester.cityValidator("!@sasdSASD"),false);
        Assert.assertEquals(tester.cityValidator("12334"),false);
        Assert.assertEquals(tester.cityValidator("Poznan"),true);
        Assert.assertEquals(tester.cityValidator("W4rszawa"),false);
        Assert.assertEquals(tester.cityValidator("GdYnIa"),true);
    }
}

