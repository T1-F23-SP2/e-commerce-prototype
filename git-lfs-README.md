# Git LFS
Git LFS allows git to manage binary data, because git is bad at binary data by default. 
Binary data is typically Images, or videos. But it can also be pdf, word, excel or any other office document. 

## GUI git client
Many Gui git clients have git LFS support.
[git fork](https://git-fork.com) has full support for git lfs, and does everything automatically.  
**It has git LFS built in, and automatically initializes git LFS in repositories.**

> **Note**  
> **All common binary file types are already set**
> to be tracked with LFS, when LFS is installed and initialized in the repository



## Git CLI
For git cli the git-lfs module should be installed manually
[git LFS](https://git-lfs.com)

### Initialize git LFS in repository
After git lfs is installed on the computer then clone repo
```sh
git clone ...
```
After repository is cloned git lfs needs to be initialized in the repository
First make sure to be inside the repository, then run this command
```sh
git lfs install
```


### [Quick start guide for CLI](https://oroshix.github.io/git/2019/04/02/initializing-git-lfs/)


# Check Git LFS works
Open the following png image in a image viewer, 
it should display a diagram of how git LFS work
> `git-lfs-success.png`

It should look like this:

![Image](./git-lfs-success.png)

If you can't see the picture, make sure you either use a GUI client with git-lfs support, or have git-lfs module installed for your cli client. (See the above)

If that doesn't work, ask on discord. (On our server, or me @AtomicHelix)
