import { Modal } from "react-bootstrap";
import { useState } from "react";
import 'bootstrap/dist/css/bootstrap.css';
import Select from 'react-select'
import '../Currency/currency.css'

const CurrencyForm = (props) => {

  const availCurrData = props.availCurrData
  const [currData, setCurrData] = useState(availCurrData[0])
  const [quantity, setQuantity] = useState(1)
  const [avgBought, setAvgBought] = useState(
    availCurrData.length > 0 ? availCurrData[0].currentPrice : 0.01)
  
  const onSubmit = (e) => {
    e.preventDefault()
    const newCurrency = {
      quantity : quantity,
      avgBought  : avgBought,
      currencyData : currData
    }
    props.onAdd(newCurrency)
  }

  const onChange = (option) => {
    setCurrData(option)
    setAvgBought(option.currentPrice)
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
        <Modal.Title>Add currency</Modal.Title>
      </Modal.Header>
     <Modal.Body>
       <div className='d-flex justify-content-center'>
        <form onSubmit={onSubmit} >
         <div className="form-group">
           <label className="form-label">Currency</label>
           <Select 
              className="form-control"
              value={currData} 
              onChange={(option) => onChange(option)}
              options={availCurrData}
              getOptionLabel={(option) => (option.currencyName + " (" + option.symbol + ")")}
              getOptionValue={(option) => option}
              isSearchable={true}
            >
           </Select>
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

export default CurrencyForm