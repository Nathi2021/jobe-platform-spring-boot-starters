package dev.jobe.platform.text.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "platform.text.transformer")
public class TextTransformerProperties {

    private boolean enabled = true;
    private Aspect aspect = new Aspect();

    public static class Aspect {
        private boolean enabled = true;
        private String[] includePatterns = {"@within(org.springframework.web.bind.annotation.RestController)"};

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String[] getIncludePatterns() {
            return includePatterns;
        }

        public void setIncludePatterns(String[] includePatterns) {
            this.includePatterns = includePatterns;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Aspect getAspect() {
        return aspect;
    }

    public void setAspect(Aspect aspect) {
        this.aspect = aspect;
    }
}
