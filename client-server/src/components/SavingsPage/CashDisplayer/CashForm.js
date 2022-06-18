import { Modal } from "react-bootstrap";
import { useState } from "react";
import 'bootstrap/dist/css/bootstrap.css';
import './cashDisplayer.css'

const CashForm = (props) => {

  const [formCash, setFormCash] = useState(props.cash())

  const onSubmit = (e) => {
    e.preventDefault()
    props.onUpdate({"amount": formCash})
  }

  return (
    <Modal
      size="sm"
      aria-labelledby="contained-modal-title-vcenter"
      centered
      show={props.showFrom}
      onHide={props.handleClose}
     > 
      <Modal.Header className="text-center" closeButton>
        <Modal.Title>Update cash</Modal.Title>
      </Modal.Header>
     <Modal.Body>
       <div className='d-flex justify-content-center'>
        <form onSubmit={onSubmit} >
         <div className="form-group">
           <label className="form-label" >Cash</label>
           <input 
            type="number"
            className="form-control"
            step={0.01}
            required={true}
            value={formCash}
            min={0.0}
            max={1000000000}
            onChange={(e) => setFormCash(e.target.value)} 
          />
         </div>
         <div className="form-group pt-2">
          <button type="submit" className="btn btn-dark form-button" >Submit</button>
         </div>
         </form>
       </div>
     </Modal.Body>
   </Modal>
  )
}

export default CashForm