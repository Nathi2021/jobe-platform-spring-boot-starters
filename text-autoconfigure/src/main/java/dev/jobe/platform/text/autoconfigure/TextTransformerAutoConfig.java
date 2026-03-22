package dev.jobe.platform.text.autoconfigure;

import dev.jobe.platform.text.core.processor.TextTransformer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@AutoConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnClass(TextTransformer.class)
@ConditionalOnProperty(
    prefix = "platform.text.transformer",
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true
)
public class TextTransformerAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public TextTransformer textTransformer() {
        return new TextTransformer();
    }

    @Bean
    @ConditionalOnMissingBean
    public TextTransformerAspect textTransformerAspect(TextTransformer textTransformer) {
        return new TextTransformerAspect(textTransformer);
    }
}
