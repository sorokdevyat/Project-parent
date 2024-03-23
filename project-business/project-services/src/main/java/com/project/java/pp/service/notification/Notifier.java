package com.project.java.pp.service.notification;
// У интерфейса есть две реализации: 1. С ипользованием rabbit(EmailGenerator), 2. Без rabbit - SimpleNotifier, в зависимости от пропертей будет использоваться та или иная реализация

public interface Notifier {
    void sendMessage(String email,String membername);
}
