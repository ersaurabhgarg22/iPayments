Hi!!

Welcome to iPayment project.

Please start ActiveMQ on your machine!!
Once done, please test if ActiveMQ is running by using below URL:
http://127.0.0.1:8161/admin/index.jsp

Login Hint: ID & Pass is: admin

After importing this project, please open below files and start the SpringBoot Projects!!

../payment-processing-system/src/main/java/com/db/iPayments/Main.java

../broker-service/src/main/java/com/db/iPayments/Main.java

../fraud-check-system/src/main/java/com/db/iPayments/Main.java

**Note:** All these project will start on different ports! If any of your app is using any of the ports then please update the port configuration from respective application.properties.

Once all the Spring-Boot projects are started, Please goto curl-commands folder and use the added file and import it to your postman as a collection.

Please use different service to test different inputs!!

Good Luck and Happy Coding!!

Important:

URLs for Solution 1 (JMS):
http://localhost:8080/payment/process

URL for Solution 2 (RestAPI):
http://localhost:8080/payment/api/process

Refer below files for UML

**Overview of project:** Overview_iPayment_UML.drawio.png

**InDepth details:** InDepth_iPayments_UMS.drawio.png
