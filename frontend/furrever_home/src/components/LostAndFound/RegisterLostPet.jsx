import {
    Card,
    CardBody,
    Dialog,
    Typography
} from "@material-tailwind/react";
import axios from 'axios';
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { readLocalStorage } from '../../utils/helper';

const RegisterLostPet = ({ setChange }) => {
    const [open, setOpen] = React.useState(false);
    const [response, setResponse] = useState({})
    const [loading, setLoading] = useState(true)
    const handleOpen = () => setOpen((cur) => !cur);
    const navigate = useNavigate();
    const sid = readLocalStorage("shelterID");
    const token = readLocalStorage("token")
    // console.log("Bearer " + token)
    // const [image, setPetImage] = useState("");

    const [formData, setFormData] = useState({
        type: "",
        breed: "",
        colour: "",
        gender: "",
        phone: "",
        email: "",
        petImage:""
    });



    const handleChange = (event) => {

        const newData = { ...formData }
        newData[event.target.id] = event.target.value
        console.log(newData)
        setFormData(newData)
    }

    const handleImage = (image) => {

        const reader = new FileReader();
        reader.readAsDataURL(image);
        reader.onload = function (e) {
              console.log(e.target.result)
            const newData = { ...formData }
            newData.petImage = e.target.result
            console.log(newData)
            setFormData(newData)
            console.log(formData)
            
        };

        reader.onerror = function () {
            console.log(reader.error);
        };
    }



    const handleSubmit = (event) => {

        event.preventDefault();

       
        console.log(formData)

        axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/petadopter/lostpet`, formData, {
            headers: {
                Authorization: "Bearer " + token,
            }
        })
            .then((res) => {
                console.log(res)
                setChange(true)
                setResponse(res)
                setLoading(true)
                toast.success("New Pet added!");
                // navigate(0)
                handleOpen();
                setFormData({
                    type: "",
                    breed: "",
                    colour: "",
                    gender: "",
                    phone: "",
                    email: "",
                    petImage: ""
                })

            })
            .catch((err) => {
                console.log(err)
                toast.error(err.message)
                handleOpen();
                setFormData({
                    type: "",
                    breed: "",
                    colour: "",
                    gender: "",
                    phone: "",
                    email: "",
                    petImage: ""
                })
            })
    }



    return (
        <>
            <button className="btn btn-orange m-5" onClick={handleOpen}>Add new Pet</button>
            <Dialog
                size="lg"
                open={open}
                handler={handleOpen}
                className="bg-transparent shadow-none"
            >
                <Card className="mx-auto w-full max-w-[30rem] ">
                    <CardBody className="flex flex-col gap-4">
                        <Typography variant="h4" color="blue-gray">
                            Register Lost Pet
                        </Typography>
                        <Typography
                            className="mb-3 font-normal"
                            variant="paragraph"
                            color="gray"
                        >
                            Enter details of new pet
                        </Typography>

                        <form method="POST" onSubmit={handleSubmit}>


                            <div>

                                <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Type
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="type"
                                        name="type"
                                        type="text"
                                        value={formData.type}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Shelter Name'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Breed
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="breed"
                                        name="breed"
                                        type="text"
                                        value={formData.breed}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Shelter Name'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Colour
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="colour"
                                        name="colour"
                                        type="text"
                                        value={formData.name}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Shelter Name'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Gender
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="gender"
                                        name="gender"
                                        type="text"
                                        value={formData.gender}
                                        onChange={handleChange}
                                        autoComplete="text"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Enter Shelter Name'
                                    />
                                </div>
                            </div>

                            <div>
                                <label htmlFor="email" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Email address
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="email"
                                        name="email"
                                        type="email"
                                        value={formData.email}
                                        onChange={handleChange}
                                        autoComplete="email"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Your Email Address'
                                    />
                                </div>
                            </div>

                            <div>

                                <label htmlFor="phone" className="text-sm font-medium leading-6 text-gray-900 flex">
                                    Phone Number
                                </label>
                                <div className="mt-1">
                                    <input
                                        id="phone"
                                        name="phone"
                                        value={formData.phone}
                                        onChange={handleChange}
                                        type="tel"
                                        autoComplete="tel"
                                        required
                                        className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                        placeholder='Your Phone Number'
                                    />
                                </div>
                            </div>


                            <div className="">
                                <label className="block text-sm font-medium leading-6 text-gray-900">Upload Image</label>
                                <input type="file"
                                    name='petImage'
                                    required
                                    onChange={(event) => { handleImage(event.target.files[0]) }}
                                    className="w-full text-black text-sm bg-white border file:cursor-pointer cursor-pointer file:border-0 file:py-2.5 file:px-4 file:bg-gray-100 file:hover:bg-gray-200 file:text-black rounded" />
                                <p className="text-xs text-gray-400 mt-2">PNG, JPG are Allowed.</p>
                            </div>

                            <div className="pt-0 flex gap-4">
                                <button type="submit" className="btn btn-orange" variant="gradient" fullWidth>
                                    Add
                                </button>
                                <button className="btn btn-orange" variant="gradient" onClick={handleOpen} fullWidth>
                                    Close
                                </button>
                            </div>


                        </form>

                    </CardBody>


                </Card>

            </Dialog>
        </>
    );
}

export default RegisterLostPet