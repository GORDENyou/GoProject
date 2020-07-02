package p.gordenyou.golibrary.log;

/**
 * log 格式化器，使用了泛型，增加了拓展性
 * @param <T>
 */
public interface GoLogFormatter<T> {
    String format(T data);
}
