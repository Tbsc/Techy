/**
 * Everything in this package can only be called on the client side.
 * That means that ALL of the client stuff here can use the common methods (that's
 * why they are common), but NONE of the common stuff can use the client stuff.
 * You can't guarantee (without checking) that you are running on the client, and therefore
 * anything that needs to be done on the client is here.
 */
package tbsc.techy.client;