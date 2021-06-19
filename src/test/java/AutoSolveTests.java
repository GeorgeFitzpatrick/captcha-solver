import com.georgefitzpatrick.captchasolver.AbstractCaptchaSolver;
import com.georgefitzpatrick.captchasolver.CaptchaRequest;
import com.georgefitzpatrick.captchasolver.autosolve.AutoSolveCredentials;
import com.georgefitzpatrick.captchasolver.autosolve.AutoSolveSolver;
import com.georgefitzpatrick.captchasolver.builder.HCaptchaRequestBuilder;
import com.georgefitzpatrick.captchasolver.builder.RecaptchaV2RequestBuilder;
import org.junit.Before;
import org.junit.Test;

public class AutoSolveTests {

    private static final String AUTO_SOLVE_CLIENT_ID = "Vortex-d0161d11-ff8d-4a37-ad75-e06627b3e249";
    private static final String AUTO_SOLVE_API_KEY = "6f206a18-ee96-4414-b45c-f1239d31ec9e";
    private static final String AUTO_SOLVE_ACCESS_TOKEN = "56419-f22dc6ad-bdf7-42e5-93c6-193fc731efd4";

    private static final String RECAPTCHA_SITE_KEY = "6LeWwRkUAAAAAOBsau7KpuC9AV-6J8mhw4AjC3Xz";
    private static final String RECAPTCHA_PAGE_URL = "https://www.supremenewyork.com/checkout/";

    private static final String HCAPTCHA_SITE_KEY = "51829642-2cda-4b09-896c-594f89d700cc";
    private static final String HCAPTCHA_PAGE_URL = "http://democaptcha.com/demo-form-eng/hcaptcha.html";

    private AbstractCaptchaSolver solver;

    @Before
    public void createSolver() {
        AutoSolveCredentials credentials = new AutoSolveCredentials.Builder()
                .apiKey(AUTO_SOLVE_API_KEY)
                .accessToken(AUTO_SOLVE_ACCESS_TOKEN)
                .build();

        solver = new AutoSolveSolver(AUTO_SOLVE_CLIENT_ID, credentials);
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