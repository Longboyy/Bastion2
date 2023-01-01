plugins {
	id("io.papermc.paperweight.userdev") version "1.3.8"
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")

	compileOnly("net.civmc.civmodcore:CivModCore:2.4.1:dev-all")
	compileOnly("net.civmc.namelayer:NameLayer:3.1.0:dev")
	compileOnly("net.civmc.citadel:Citadel:5.1.0:dev")
	implementation("io.github.earcut4j:earcut4j:2.2.2")
}
