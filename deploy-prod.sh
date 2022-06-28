#!/bin/bash

echo "deploying in PROD"
 gcloud functions deploy func_pay_sprint --set-env-vars GOOGLE_CLOUD_PROJECT=mbnk-integrations --entry-point io.micronaut.gcp.function.http.HttpFunction --runtime java11 --trigger-http --memory 512MB --allow-unauthenticated --region=asia-south1 --project=mbnk-integrations
echo 'deployment succesful.......'
