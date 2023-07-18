import subprocess


def build_jar():
    subprocess.run(["javac", "*.java"], check=True)
    subprocess.run(["jar", "cf", "BeePoint.jar", "*.class"], check=True)
    print("File JAR Success")

def clean():
    subprocess.run(["rm", "-f", "*.class"])
    subprocess.run(["rm", "-f", "BeePoint.jar"])
    print("Clean Success")



tag = input("input version tag x.x.x : ")
clean()
build_jar()
subprocess.run(["docker", "build", "-t", "bc-back", "."], check=True)
tagged_image = f"sayamphoo/bc-back:{tag}"
subprocess.run(["docker", "tag", "bc-back", tagged_image], check=True)
subprocess.run(["docker", "push", tagged_image], check=True)
clean()