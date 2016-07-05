# Techy
Tech mod for 1.9.4+

**NOTE: Techy is being rewritten currently! Expect *little to no support* for Techy 1.x.x.**

License: LGPLv3. TL;DR: You can copy, distribute, link proprietary code and modify the software as long as you track changes in source files. Any modifications to or software including (via compiler) my code must also be made available under the GPL license too.


## IMC Recipe Support

In order to add recipes to Techy through IMC (Inter-Mod Communication), a specific format of message needs to be sent.
The base of the format is similar to all recipe messages, but with specific additions for each machine.

All messages need to have a string with key **RecipeType**, and the values can be either:
- PoweredFurnace
- Crusher

Almost all machines will need an input object. The input object must be stored with the key **Input**, and must contain either an ItemStack or an NBTTagString (ore name, and note that you CAN **NOT** use setString(), you must use setTag() and give it an NBTTagString instance).
Another mandatory object is the output ItemStack, with the key **Output** and an ItemStack value (NOTE: Use ItemStack#writeToNBT(NBTTagCompound) for that).
All machines require energy to operate, and therefore a mandatory object is the amount of energy that will be consumed by this recipe. It must give an integer, and its key must be **EnergyUsage**.

| Object               | Key                | Type                              |
|----------------------|--------------------|-----------------------------------|
| Input object         | Input              | ItemStack/NBTTagString (ore name) |
| Output stack         | Output             | ItemStack                         |
| Energy usage         | EnergyUsage        | Integer                           |

That's it for objects that must be in all recipes. Now for the specific ones.

### Crusher

The crusher needs to also know the second output stack, and the chance to get it.

| Object               | Key                | Type      |
|----------------------|--------------------|-----------|
| Second output stack  | SecondOutput       | ItemStack |
| Second output chance | SecondOutputChance | Integer   |
