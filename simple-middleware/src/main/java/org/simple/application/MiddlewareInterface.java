package org.simple.application;

import java.util.Map.Entry;

public interface MiddlewareInterface {
    Entry<Object,Object> current(Entry<Object, Object> intercept);
    Entry<Object,Object> next(Entry<Object,Object> intercept); // default implementation: call to the dispatcher servlet
}
