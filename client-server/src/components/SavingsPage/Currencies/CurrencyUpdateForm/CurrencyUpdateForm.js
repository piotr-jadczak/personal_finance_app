import { Modal } from "react-bootstrap";
import { useState } from "react";
import 'bootstrap/dist/css/bootstrap.css';
import '../Currency/currency.css'

const CurrencyUpdateForm = (props) => {

  const [quantity, setQuantity] = useState(props.quantity)
  const [avgBought, setAvgBought] = useState(props.avgBought)
  const id = props.id

  const onSubmit = (e) => {
    e.preventDefault()
    props.onUpdate(id, {quantity : quantity, avgBought : avgBought })
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
        <Modal.Title>Update currency</Modal.Title>
      </Modal.Header>
     <Modal.Body>
       <div className='d-flex justify-content-center'>
        <form onSubmit={onSubmit} >
          <div className="form-group">
            <a className="form-update-label">Currency</a><br/>
            <a className="form-update-name">{props.currencyName}</a>
          </div>
         <div className="form-group">
           <label className="form-label">Quantity</label>
           <input 
            type="number"
            className="form-control"
            step={1}
            required={true}
            value={quantity}
            min={1}
            max={1000000}
            onChange={(e) => setQuantity(e.target.value)} 
          />
         </div>
         <div className="form-group">
           <label className="form-label">Avg Bought</label>
           <input 
            type="number"
            className="form-control"
            step={0.01}
            required={true}
            value={avgBought}
            min={0.01}
            max={1000000}
            onChange={(e) => setAvgBought(e.target.value)} 
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

export default CurrencyUpdateForm