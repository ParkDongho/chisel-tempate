help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

run: ## compile chisel to sv
	sbt run

test: ## run Treadle
	sbt test

vivado: ## Open the Vivado
	bash ./scripts/fpga/vivado_project.sh &

clean:
	sbt clean
	rm *.fir *.sv *.v *.fir.mlir *.anno.json

publish:
	sbt PublishLocal

scaladoc:
	sbt doc && \
	  cd ./target/scala-2.13/api/ && \
	  python3 -m "http.server 8080 &
