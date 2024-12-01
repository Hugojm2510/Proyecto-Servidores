# Proyecto Servidores (DAW)

# Indice

1. [Descripción](#descripción)
2. [Métodos Utilizados](#métodos-utilizados)
    - [Project](#project)
    - [Developers](#developers)
    - [Technologies](#technologies)
3. [DTO](#dto)
4. [Métodos para agregar un Dev o Tech a un Project](#métodos-para-agregar-un-dev-o-tech-a-un-project)
5. [Error que he tenido](#error-que-he-tenido)

## Descripción

- Esta API esta formado por un Proyecto en el cual esta enlazado a un Status, además de va a tener listas de Developers
  y Technologies.
  Lo he puesto de tal manera que va cuando se muestre el proyecto, en el apartado de *Developers* se muestren: **Id**, **Nombre** y **Apellido** y en el apartado de *Tecnologies* se muestren: **Id**, **Nombre**. 

## Métodos Utilizados

### Project

- getAllProjects: **mostrar todos los proyectos**
- getProjectByPrefix: **mostrar los proyectos que empiecen por la palabra que busque**
- createProject: **crear un proyecto**
- updateProject: **modificar un proyecto**
- deleteProject: **borrar un proyecto**


### Developers

- createDeveloper: **crear un developer**
- deleteDeveloper: **borrar un developer**
- addDeveloperToProject: **agregar un developer a un proyecto**


### Technologies

- createTechnologies: **crear una technology**
- deleteTechnologies: **borrar una technology**
- addTechnologieToProject: **agregar una technology a un proyecto**


## DTO

- Para el dto como no quiero que se muestren todos los datos, entonces para **Project** y **Developer**, como tienen muchos
  datos he creado un DTO para crearlo cada uno con la estructura que yo quiero y otro DTO para que muestre una estructura
  con los datos importantes.

``` java
public CreateProjectDTO(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.description = project.getDescription();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.repositoryUrl = project.getRepositoryUrl();
        this.demoUrl = project.getDemoUrl();
        this.picture = project.getPicture();
        this.statusId = project.getStatus().getStatusId();
    }
```

```java
public ProjectDTO(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.description = project.getDescription();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.repositoryUrl = project.getRepositoryUrl();
        this.demoUrl = project.getDemoUrl();
        this.picture = project.getPicture();
        this.status = project.getStatus().getStatusName();

        this.developers = new ArrayList<>();
        for (Developer developer : project.getDevelopers()) {
            DeveloperDTO developerdto = new DeveloperDTO(developer);
            this.developers.add(developerdto);
        }

        this.technologies = new ArrayList<>();
        for (Technologies technologie : project.getTechnologies()) {
            TechnologiesDTO techdto = new TechnologiesDTO(technologie);
            this.technologies.add(techdto);
        }
    }
```


## Metodos para agregar un Dev o Tech a un project

- El metodo que he utilizado para insertar devs o techs es este:

``` java
@PutMapping("/worked/{projectId}/{devId}")
    public ResponseEntity<ProjectDTO> addDeveloperToProject(@PathVariable Integer projectId, @PathVariable Integer devId) {
        try {
            Project project = devMngmnt.addDeveloperToProject(projectId, devId);
            return ResponseEntity.ok(new ProjectDTO(project));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

```

- Lo que hago es poner el id del proyecto que voy a utilizar y el id del dev que quiero agregar.


## Error que he tenido

- Al crear un dev o tech me generaba una lista de project[]. Lo que hice fue crear el DeveloperDTO y en la
  estructura puse que se mostraran los datos que yo quería y así no me mostrara project[].
