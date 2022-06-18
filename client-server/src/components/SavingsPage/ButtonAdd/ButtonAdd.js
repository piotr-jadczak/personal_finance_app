import 'bootstrap/dist/css/bootstrap.css';
import { BsPlusSquare } from "react-icons/bs";
import './buttonAdd.css'

const ButtonAdd = (props) => {

  return (
    <div className='add-button' onClick={props.handleShow}>
      <BsPlusSquare size={92}
      style={{ color: 'white' }} 
      />
      <br />
      <a>{props.description}</a>
      <br />
    </div>
  )
}

export default ButtonAdd