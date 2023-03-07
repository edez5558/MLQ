# MLQ usando hilos

Gomez Alvarez Edmundo

# Introduccion
Este algoritmo de planificación clasifica los procesos en diferentes grupos, de
forma que podemos asignarlos a diferentes colas con distinta planificación para
gestionarlos de la manera que realmente necesitan. Los procesos se asignan
permanentemente a una cola del sistema, generalmente en función de alguna
propiedad del proceso, por ejemplo, el tamaño de memoria, la prioridad del
proceso o el tipo de proceso. Por ejemplo, tenemos el grupo de procesos
foreground (interactivos) y background (batch), que necesitan diferentes tiempos
de respuesta. Cada uno de ellos estará gestionado en una cola distinta con un
algoritmo de planificación distinto, por ejemplo, la cola de procesos foreground con
Round Robin, y la de procesos background con FCFS. Además, debe existir un
criterio de planificación entre las colas. Puede ser de prioridad fija con requisa o
sin requisa, o de prioridad variable con requisa o sin requisa.

Características:

Es apropiativa, es decir si llega un proceso con mayor prioridad que el que
se está ejecutando podrá expulsarle y apropiarse del procesador.

Cada cola tendrá una prioridad interna, de acuerdo a su algoritmo de
planificación. Y cuando un proceso entre en la cola, automáticamente se
calculará su prioridad interna. Esto no afectará al funcionamiento global de
las colas múltiples.

El proceso que se ejecutará será el de mayor prioridad. Y si hubiera varios,
se elegirá el mayor según las normas de las políticas de gestión
correspondientes

![IMG1](https://user-images.githubusercontent.com/122659695/223318999-5009e074-d501-4d13-97fb-4c33b2f725a3.png)
![IMG2](https://user-images.githubusercontent.com/122659695/223319002-2c923259-c7fe-4318-a064-c39f3aadbece.png)
![IMG3](https://user-images.githubusercontent.com/122659695/223319003-015a0147-f6c6-47a3-9102-6fa39d5d5080.png)
