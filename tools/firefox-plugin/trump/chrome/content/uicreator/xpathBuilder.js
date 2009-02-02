/**

    Build xpath based on a set of attributes

 **/

function XPathBuilder(){
    
    this.constants = {
        CHILD : "child",
        DESCENDANT : "descendant",
        DESCENDANT_OR_SELF : "descendant-or-self",
        SELF : "self",
        FOLLOWING : "following",
        FOLLOWING_SIBLING : "following-sibling",
        PARENT : "parent",
        DESCENDANT_OR_SELF_PATH : "/descendant-or-self::",
        CHILD_PATH : "/child::",
        DESCENDANT_PREFIX : "descendant::",
        CHILD_PREFIX : "child::",
        MATCH_ALL : "*",
        CONTAIN_PREFIX : "%%"
    };

}