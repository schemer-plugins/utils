# utils

Utility framework/API used by various other plugins. Due to how the Twilite
client works, this repository should be added as a Git submodule to every
plugin that uses it.

From the plugin root, execute the following command to add utils as a
submodule.

For public GitHub (this is the one you'll want)

```bash
git submodule add https://github.com/schemer-plugins/utils src/main/java/be/moens/schemer/utils
```

For TwiLite GitHub

```bash
git submodule add https://github.com/twilite-plugins/utils src/main/java/be/moens/schemer/utils
```

For personal git server

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
