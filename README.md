## Micronaut 3.5.1 Documentation

- [User Guide](https://docs.micronaut.io/3.5.1/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.5.1/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.5.1/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

Select project

For QA:
```bash
  gcloud config set project mbnk-integrations-qa
```

OR For PROD:
```bash
  gcloud config set project mbnk-integrations
```

## Deploying the function

First build the function with:

```bash
$ ./gradlew clean shadowJar
```

Then `cd` into the `build/libs` directory (deployment has to be done from the location where the JAR lives):

```bash
$ cd build/libs
```

For QA:
```bash
$ gcloud functions deploy fasttag --set-env-vars GOOGLE_CLOUD_PROJECT=mbnk-integrations-qa,MICRONAUT_ENVIRONMENTS=qa --entry-point io.micronaut.gcp.function.http.HttpFunction --runtime java11 --trigger-http --memory 512MB --region=asia-south1
```

Or for Prod, run:

```bash
  gcloud functions deploy fasttag --set-env-vars GOOGLE_CLOUD_PROJECT=mbnk-integrations,MICRONAUT_ENVIRONMENTS=prod --entry-point io.micronaut.gcp.function.http.HttpFunction --runtime java11 --trigger-http --memory 512MB --region=asia-south1
```

Choose unauthenticated access if you don't need auth.

To obtain the trigger URL do the following:

```bash
$ YOUR_HTTP_TRIGGER_URL=$(gcloud functions describe func_mera_adhikaar --format='value(httpsTrigger.url)')
```

You can then use this variable to test the function invocation:

```bash
$ curl -i $YOUR_HTTP_TRIGGER_URL/func_mera_adhikaar
```
## Feature google-cloud-function documentation

- [Micronaut Google Cloud Function documentation](https://micronaut-projects.github.io/micronaut-gcp/latest/guide/index.html#simpleFunctions)



