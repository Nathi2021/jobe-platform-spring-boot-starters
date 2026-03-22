//package dev.jobe.platform.transformer.core.wrapper;
//
//import dev.jobe.platform.transformer.core.processor.TextTransformer;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class ResponseWrapperTransformer {
//
//    private final TextTransformer textTransformer;
//    private final List<WrapperHandler> handlers = new ArrayList<>();
//
//    public ResponseWrapperTransformer(TextTransformer textTransformer) {
//        this.textTransformer = textTransformer;
//        this.registerHandlers();
//    }
//
//    private void registerHandlers() {
//        this.handlers.add(new ResponseEntityHandler());
//        this.handlers.add(new OptionalResponseHandler());
//    }
//
//    static class ResponseEntityHandler implements WrapperHandler {
//        @Override
//        public boolean canHandle(Object obj) {
//            return obj.getClass().getName().contains("ResponseEntity");
//        }
//
//        @Override
//        public Object transform(Object obj, TextTransformer transformer) {
//            var method = obj.getClass().getMethod("getBody");
//            Object body = method.invoke(obj);
//            if (body != null) {
//                Object transformed = transformer.transform(body);
//                var builderMethod = obj.getClass().getMethod("status", int.class);
////                return ResponseEntity.status(builderMethod.)
////                    .status(builderMethod.)
////                    .headers(responseEntity.getHeaders())
////                    .body(textTransformer.transform(body));
//            }
//            return null;
//        }
//    }
//
//
//    static class OptionalResponseHandler implements WrapperHandler {
//        @Override
//        public boolean canHandle(Object obj) {
//            return obj instanceof Optional<?>;
//        }
//
//        @Override
//        public Object transform(Object obj, TextTransformer transformer) {
//            return ((Optional<?>) obj).map(transformer::transform);
//        }
//    }
//}
