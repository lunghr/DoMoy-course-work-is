const socket1 = new SockJS('http://localhost:8080/ws/chat');  // User 1 WebSocket
const socket2 = new SockJS('http://localhost:8080/ws/chat');  // User 2 WebSocket
const stompClient1 = Stomp.over(socket1);
const stompClient2 = Stomp.over(socket2);

// Получаем элементы для обоих пользователей
const messageInput1 = document.getElementById('messageInput1');
const sendMessageButton1 = document.getElementById('sendMessageButton1');
const messagesContainer1 = document.getElementById('messages1');
const chatSelect1 = document.getElementById('chatSelect1');

const messageInput2 = document.getElementById('messageInput2');
const sendMessageButton2 = document.getElementById('sendMessageButton2');
const messagesContainer2 = document.getElementById('messages2');
const chatSelect2 = document.getElementById('chatSelect2');

// JWT токены для двух пользователей
const tokenUser1 = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsYWxhYWxAZ21haWwuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlkIjoxLCJlbWFpbCI6ImxhbGFhbEBnbWFpbC5jb20iLCJpYXQiOjE3NDEwNDM1MzQsImV4cCI6MTc0MTA3OTUzNH0.nHBlytLP6F2zUHJuIDle4OCvxodSTiJ0ZImeiI5W-hQ";  // Замените на реальный токен User 1
const tokenUser2 = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsYWxhbGxhQGdtYWlsLmNvbSIsImlhdCI6MTc0MTA0MzU0NSwiZXhwIjoxNzQxMDc5NTQ1fQ.tXr11Tyr8QARxYavsXPA-cS8Z2QG42HhZnyo2IyXRvU";  // Замените на реальный токен User 2

// Функция для обработки входящих сообщений
function onMessageReceived(messageOutput, user) {
    // Парсим сообщение из JSON
    const message = JSON.parse(messageOutput.body);


    // Создаем элемент для отображения сообщения
    const messageElement = document.createElement("div");
    messageElement.classList.add("message");

    // Вставляем имя чата и текст сообщения в элемент
    messageElement.innerText = `[${message.chatName}] ${message.text}`;

    // Добавляем сообщение в контейнер в зависимости от пользователя
    if (user === 1) {
        messagesContainer1.appendChild(messageElement);
    } else {
        messagesContainer2.appendChild(messageElement);
    }
}

// Подключаемся к STOMP серверу для User 1
stompClient1.connect({}, function (frame) {
    console.log('User 1 connected: ' + frame);

    // Подписка на выбранный чат User 1
    stompClient1.subscribe(`/topic/chat/1`, function (message) { onMessageReceived(message, 1); });
    stompClient1.subscribe(`/topic/chat/2`, function (message) { onMessageReceived(message, 1); });
    stompClient1.subscribe(`/topic/chat/3`, function (message) { onMessageReceived(message, 1); });

    // Обработчик кнопки отправки сообщения для User 1
    sendMessageButton1.addEventListener('click', function() {
        const chatId = chatSelect1.value;
        const message = { content: messageInput1.value };
        const headers = { 'Authorization': tokenUser1 };

        stompClient1.send(`/app/chat.sendMessage/${chatId}`, headers, JSON.stringify(message));  // Отправка сообщения в выбранный чат User 1
        messageInput1.value = '';  // Очищаем поле ввода
    });
});

// Подключаемся к STOMP серверу для User 2
stompClient2.connect({}, function (frame) {
    console.log('User 2 connected: ' + frame);

    // Подписка на выбранный чат User 2
    stompClient2.subscribe(`/topic/chat/1`, function (message) { onMessageReceived(message, 2); });
    stompClient2.subscribe(`/topic/chat/2`, function (message) { onMessageReceived(message, 2); });
    stompClient2.subscribe(`/topic/chat/3`, function (message) { onMessageReceived(message, 2); });

    // Обработчик кнопки отправки сообщения для User 2
    sendMessageButton2.addEventListener('click', function() {
        const chatId = chatSelect2.value;
        const message = { content: messageInput2.value };
        const headers = { 'Authorization': tokenUser2 };

        stompClient2.send(`/app/chat.sendMessage/${chatId}`, headers, JSON.stringify(message));  // Отправка сообщения в выбранный чат User 2
        messageInput2.value = '';  // Очищаем поле ввода
    });
});
