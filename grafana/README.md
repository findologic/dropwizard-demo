# Grafana Docker image

To speed things up, the `Dockerfile` in this directory modifies Grafana so it
already has a pre-configured Dashboards for several metrics. You don't need to
do anything here - `../docker-compose.yml` takes care of building the image.