import com.georgefitzpatrick.captchasolver.AbstractCaptchaSolver;
import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import com.georgefitzpatrick.captchasolver.builder.HCaptchaRequestBuilder;
import com.georgefitzpatrick.captchasolver.builder.NormalCaptchaRequestBuilder;
import com.georgefitzpatrick.captchasolver.builder.RecaptchaV2RequestBuilder;
import com.georgefitzpatrick.captchasolver.capmonster.CapMonsterSolver;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class CapMonsterTests {

    private static final String CAP_MONSTER_API_KEY = "ce3fb5e8e99cb69755c4cd90e6404113";

    private static final String RECAPTCHA_SITE_KEY = "6LeWwRkUAAAAAOBsau7KpuC9AV-6J8mhw4AjC3Xz";
    private static final String RECAPTCHA_PAGE_URL = "https://www.supremenewyork.com/checkout/";

    private static final String HCAPTCHA_SITE_KEY = "51829642-2cda-4b09-896c-594f89d700cc";
    private static final String HCAPTCHA_PAGE_URL = "http://democaptcha.com/demo-form-eng/hcaptcha.html";

    private AbstractCaptchaSolver solver;

    @Before
    public void createSolver() {
        solver = new CapMonsterSolver(CAP_MONSTER_API_KEY);
    }

    @Test
    public void testNormalCaptcha() throws Exception {
        CaptchaRequest request = new NormalCaptchaRequestBuilder()
                .calculate(false)
                .caseSensitive(true)
                .image(new File("/Users/george/Code/George Fitzpatrick/captcha-solver/src/test/resources/captcha.png"))
                .language("en")
                .maxLength(10)
                .minLength(1)
                .phrase(false)
                .build();

        assert solver.solve(request).getResult() != null;
    }

    @Test
    public void testRecaptchaV2Checkbox() throws Exception {
        CaptchaRequest request = new RecaptchaV2RequestBuilder(true)
                .siteKey(RECAPTCHA_SITE_KEY)
                .url(RECAPTCHA_PAGE_URL)
                .build();

        assert solver.solve(request).getResult() != null;
    }

    @Test
    public void testRecaptchaV2Invisible() throws Exception {
        CaptchaRequest request = new RecaptchaV2RequestBuilder(false)
                .siteKey(RECAPTCHA_SITE_KEY)
                .url(RECAPTCHA_PAGE_URL)
                .build();

        assert solver.solve(request).getResult() != null;
    }

    @Test
    public void testRecaptchaV3() {

    }

    @Test
    public void testHCaptchaCheckbox() throws Exception {
        CaptchaRequest request = new HCaptchaRequestBuilder(true)
                .siteKey(HCAPTCHA_SITE_KEY)
                .url(HCAPTCHA_PAGE_URL)
                .build();

        assert solver.solve(request).getResult() != null;
    }

    @Test
    public void testHCaptchaInvisible() throws Exception {
        CaptchaRequest request = new HCaptchaRequestBuilder(false)
                .siteKey(HCAPTCHA_SITE_KEY)
                .url(HCAPTCHA_PAGE_URL)
                .build();

        assert solver.solve(request).getResult() != null;
    }

}