1. Список клиентов с их аккаунтами доступен через API http://localhost:8080/api/client/list
2. Экспорт доступен через API XML http://localhost:8080/api/client/list/export-xml
3. Транзакция доступна через API http://localhost:8080/api/transaction/transfer?fromAccountId=2&toAccountId=4&amount=200
4. Импорт списка клиентов с их счетами доступен через API http://localhost:8080/api/client/import-xml, где в теле реквеста отправляется xml
