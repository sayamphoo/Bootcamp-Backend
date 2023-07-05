import subprocess

tag = input("input version tag x.x.x : ")
subprocess.run(["docker", "build", "-t", "bc-back", "."], check=True)
tagged_image = f"sayamphoo/bc-back:{tag}"
subprocess.run(["docker", "tag", "bc-back", tagged_image], check=True)
subprocess.run(["docker", "push", tagged_image], check=True)
