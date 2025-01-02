import { InputBase, ListItem, ListItemText } from "@material-ui/core";
import { CheckBox } from "@material-ui/icons";
import React from "react";

class Todo extends React.Component{
    constructor(props){
        super(props);
        this.state = { item: props.item};
    }
    render(){
        const item = this.state.item;
        return(
            <ListItem>
                <CheckBox checked ={item.done}/>
                <ListItemText>
                    <InputBase
                        inputProps={{"aria-label":"naked"}}
                        type="text"
                        id={item.id}
                        name={item.id}
                        value={item.title}
                        multiline={true}
                        fullWidth={true}
                    />
                </ListItemText>
            </ListItem>
        )
    }
}

export default Todo;