# CHOCO app
![2021-11-12 23 56 15](https://user-images.githubusercontent.com/9511896/141496199-5ae9df8f-363a-4280-85e2-ef95d1c0d77f.gif)

** Login Credentials:**

**User:** user@choco.com

**Password:** chocorian
 
**Branding**

- Copied colors and styles from Google play photos.

**Modules**

- core: has extension functions and some crucial classes for all modules.
- core-ui: has the styles, dimens and other UI components that is belived to be needed by other modules.
- browse: a self-contained module that has Dao class, models and viewmodels encapsulating all the logic for selecting products and placing orders.
- account: has the profile fragment.
- auth: encapsulates the login flow (network, view , viewmodels model layers) 
- cache: has the db initialisation.
- app: depends on all previous modules but has minimum code for inter-module navigation and dependency injection.

Lastly 
- test: has particular classes that should never be in the prod code like `TestDispatchersProvider`.



**Single activity architecture!**

- The app has many fragments but only one activity. This will serve us well keeping some viewmodels alive as long as the app is running. 

![Screenshot 2021-11-12 at 11 47 19 PM](https://user-images.githubusercontent.com/9511896/141495118-2baea751-a804-4308-b88d-b27119891bfe.png)

All thanks to nav component!

** Honorable mentions 

- Custom component but number button. ![2021-11-12 23 30 26](https://user-images.githubusercontent.com/9511896/141496819-3609a862-8afd-41d8-bb4e-1a577b94fc13.gif)

- Error handling and loading states everywhere. 

|   |   |
| ------ | ------ |
|  ![screenshot-1636732541479](https://user-images.githubusercontent.com/9511896/141497473-4a8abd57-3ec2-49aa-a321-2f76fb0ac6d6.png) |  ![screenshot-1636732550907](https://user-images.githubusercontent.com/9511896/141497524-092031eb-872c-48c7-9433-d6610a258834.png) |
|  ![screenshot-1636733130104](https://user-images.githubusercontent.com/9511896/141497608-bc58b45f-611f-43ea-8b83-a5f88a01a857.png) | ![screenshot-1636733115694](https://user-images.githubusercontent.com/9511896/141497704-06a76e28-92b3-4ee3-9321-b36770f005f6.png)  | 

Would be happy to go through it over a friendly chat.



** Testing ** 
- Unit test for model layer and viewmodel layer with proper dispatchers swapping.
