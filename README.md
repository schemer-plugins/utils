# utils

Utility framework/API used by various other plugins. Due to how the Twilite
client works, this repository should be added as a Git submodule to every
plugin that uses it.

From the plugin root, execute the following command to add utils as a
submodule.

```bash
git submodule add https://git.loki.moens.be/schemer/utils src/main/java/be/moens/schemer/utils
```

To update the repository, execute

```bash
git submodule update --recursive --remote
```

To initialize the submodule when checking out an existing plugin

```bash
git submodule update --init --recursive
```
